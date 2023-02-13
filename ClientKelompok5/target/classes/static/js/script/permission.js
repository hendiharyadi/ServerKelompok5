const URL = "/api/permission";
let stock = 0;
const notFoundWrapper = document.getElementById("data-not-found");
const tableContentWrapper = document.getElementById("table-content");
const loadedData = async () => {
  await loadTable();
};

const loadTable = async () => {
  await getStock();
  document
    .getElementById("trigger-addPermission")
    .addEventListener("click", preAddPermission);

  try {
    const res = await fetch(URL);
    if (!res.ok) {
      Swal.fire({
        icon: "error",
        title: "Not Found",
        text: "Failed to load data permission!",
      });
    }
    const json = await res.json();
    const tableWrapper = document.getElementById("table-wrapper");
    tableWrapper.innerHTML = "";
    let i = 0;
    if (json.length !== 0) {
      tableContentWrapper.classList.remove("d-none");
      notFoundWrapper.classList.add("d-none");
      const sortedData = json.sort((a, b) => b.id - a.id);
      console.log(json);
      json.forEach((p) => {
        i += 1;
        tableWrapper.innerHTML += tableContent(
          i,
          p.id,
          p.leave_type,
          p.start_leave,
          p.end_leave,
          p.status
        );
      });
    } else {
      tableContentWrapper.classList.add("d-none");
      notFoundWrapper.classList.remove("d-none");
    }
  } catch (e) {
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: "Something went wrong!",
    });
    console.log(e);
  }
};

const preAddPermission = () => {
  const noteWrapper = document.getElementById("note-add-wrapper");
  const permissionType = document.getElementById("input-permissionType");
  permissionType.addEventListener("change", (event) => {
    const value = event.target.value;
    if (value === "1") {
      $("#input-note").val("");
      noteWrapper.classList.add("d-none");
    } else {
      $("#input-note").val("");
      noteWrapper.classList.remove("d-none");
    }
  });

  const dateNow = currentDate();
  $("#input-date-start").attr("min", dateNow);
  $("#input-date-end").attr("min", dateNow);
};

const days = (date_1, date_2) => {
  let difference = date_1 - date_2;
  let TotalDays = Math.ceil(difference / (1000 * 3600 * 24));
  return TotalDays;
};

const currentDate = () => {
  const dtToday = new Date();

  let month = dtToday.getMonth() + 1;
  let day = dtToday.getDate();
  const year = dtToday.getFullYear();
  if (month < 10) month = "0" + month.toString();
  if (day < 10) day = "0" + day.toString();

  return year + "-" + month + "-" + day;
};

const submitPermission = async () => {
  const btnSpinner = document.getElementById("spinner-button");
  const btnSubmit = document.getElementById("btn-submit");
  const start_leave = document.getElementById("input-date-start").value;
  const end_leave = document.getElementById("input-date-end").value;
  const leave_type = $("#input-permissionType").find(":selected").val();
  const note = $("#input-note").val();
  let leave_day;

  if (leave_type === "1") {
    const start = new Date(start_leave).getTime();
    const end = new Date(end_leave).getTime();
    leave_day = days(end, start);
    if (leave_day > stock || leave_day === stock) {
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: "Can't request for cuti because the amount of cuti requested is bigger than the amount of cuti left.",
      });
      return;
    }
  } else if (leave_type === "2") {
    if (note.length === 0) {
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: "Fill in the data completely!",
      });
      return;
    }
  }

  if (
    leave_type.length !== 1 ||
    start_leave.length === 0 ||
    end_leave.length === 0
  ) {
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: "Fill in the data completely!",
    });
    return;
  }

  btnSpinner.classList.remove("d-none");
  btnSubmit.classList.add("d-none");
  $.ajax({
    url: URL,
    method: "POST",
    dataType: "JSON",
    beforeSend: addCsrfToken(),
    data: JSON.stringify({
      leave_type: leave_type === "1",
      start_leave,
      end_leave,
      note,
      status: false,
      leave_day: 1,
    }),
    contentType: "application/json",
    success: async (result) => {
      console.log({ result });
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Saved!",
        showConfirmButton: false,
        timer: 1500,
      });
      btnSpinner.classList.add("d-none");
      btnSubmit.classList.remove("d-none");
      $("#modalAddItem").modal("hide");
      await loadTable();
      $("#input-date-start").val("");
      $("#input-date-end").val("");
      $("#input-permissionType").val("0").change();
    },
    error: function (xhr, ajaxOptions, thrownError) {
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: "Something went wrong!",
      });
      btnSpinner.classList.add("d-none");
      btnSubmit.classList.remove("d-none");
      console.log({ xhr, ajaxOptions, thrownError });
    },
  });
};

const tableContent = (no, id, leave_type, start_date, end_date, status) => {
  let classStatus = "";
  if (status === "PENDING") {
    classStatus = "bg-warning";
  } else if (status === "APPROVED") {
    classStatus = "bg-success";
  } else {
    classStatus = "bg-danger";
  }
  return ` <tr>
              <td>${no}</td>
              <td>${leave_type}</td>
              <td>${moment(start_date).format("ll")}</td>
              <td>${moment(end_date).format("ll")}</td>
              <td>
                <label class="badge ${classStatus}">${status}</label>
              </td>
              <td>
                <label
                  class="badge bg-primary pointer"
                  data-bs-toggle="modal"
                  data-bs-target="#modalDetailLeave"
                  onclick="detailPermission(${id})"
                >
                <i class="mdi mdi-looks"></i>
                  Detail
                </label>
              </td>
            </tr>`;
};

const checkStock = async () => {
  const res = await fetch(`/api/employee/stock-leave`);
  const data = res.json();
  const json = await data;
  const { stock_available } = json;
  let styleClass = "";
  const stockLeave = parseInt(stock_available);
  if (stockLeave === 0) {
    styleClass = "text-danger";
  } else if (stockLeave > 0 && stockLeave <= 5) {
    styleClass = "text-warning";
  } else {
    styleClass = "text-success";
  }
  Swal.fire({
    title: `<strong class="${styleClass}" style="font-size: 90px">${stock_available}</strong>`,
    html: `<span>days left for your cuti to be taken</span>`,
  });
};

const getStock = async () => {
  const stockWrapper = document.getElementById("table-stock-wrapper");
  stockWrapper.inner = "";
  const res = await fetch(`/api/employee/stock-leave`);
  const data = res.json();
  const json = await data;
  const { stock_available } = json;
  stock = stock_available;

  stockWrapper.innerHTML = tableLeaveStock(stock_available);
};

const tableLeaveStock = (stock_available) => {
  console.log(stock_available);
  let badgeBg;

  if (stock_available <= 5 && stock_available >= 1) {
    badgeBg = "bg-warning";
  } else if (stock_available >= 6) {
    badgeBg = "bg-success";
  } else {
    badgeBg = "bg-danger";
  }
  return `<tr>
              <th scope="row">1</th>
              <td>
                <span class="badge ${badgeBg} bg-gradient">${stock_available}</span>
              </td>
            </tr>`;
};

const detailPermission = async (id) => {
  try {
    const response = await fetch(`${URL}/${id}`);
    if (!response.ok) {
      Swal.fire({
        icon: "error",
        title: "Not Found",
        text: "Failed to load data permission!",
      });
    }
    const data = await response.json();
    const pType = document.getElementById("detail-permission-type");
    const start = new Date(data.start_leave).toISOString().slice(0, 10);
    const end = new Date(data.end_leave).toISOString().slice(0, 10);
    pType.add(generateOption(data.id, data.leave_type, true));
    $("#detail-date-start").val(start);
    $("#detail-date-end").val(end);
    const detailNote = document.getElementById("detail-note");
    detailNote.innerText = data.note;
    const noteWrapper = document.getElementById("detail-note-wrapper");
    if (data.leave_type === "CUTI") {
      noteWrapper.classList.add("d-none");
    } else {
      noteWrapper.classList.remove("d-none");
    }
  } catch (e) {
    console.log(e);
  }
};

const generateOption = (id, name, isSelected) => {
  const option = document.createElement("option");
  option.value = id;
  option.text = name;
  option.selected = isSelected;
  return option;
};

window.addEventListener("load", loadedData);
