const loadedData = async () => {
  document
    .getElementById("trigger-addPermission")
    .addEventListener("click", preAddEmployee);

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
  } catch (e) {
    console.log(e);
  }
};

const URL = "/api/permission";

const preAddEmployee = () => {
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
};

const submitPermission = () => {
  const start_leave = document.getElementById("input-date-start").value;
  const end_leave = document.getElementById("input-date-end").value;
  const leave_type = $("#input-permissionType").find(":selected").val();
  const note = $("#input-note").val();
  console.log({
    leave_type: leave_type === "1",
    start_leave,
    end_leave,
    note,
    status: false,
  });

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
    }),
    contentType: "application/json",
    success: (result) => {
      console.log({ result });
      Swal.fire("Saved!", "", "success").then(
        (r) => (window.location.href = "")
      );
    },
    error: function (xhr, ajaxOptions, thrownError) {
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: "Something went wrong!",
      });
      console.log({ xhr, ajaxOptions, thrownError });
    },
  });
};

const tableContent = (no, id, leave_type, start_date, end_date, status) => {
  /* const start = new Date(start_date).toISOString().slice(0, 10);
  const end = new Date(end_date).toISOString().slice(0, 10);*/

  return ` <tr>
              <td>${no}</td>
              <td>${leave_type}</td>
              <td>${start_date}</td>
              <td>${end_date}</td>
              <td>
                <label class="badge ${
                  status === "PENDING" ? "bg-warning" : "bg-success"
                }">${status}</label>
              </td>
              <td>
                <label
                  class="text-primary pointer"
                  data-bs-toggle="modal"
                  data-bs-target="#modalDetailLeave"
                  onclick="detailEmployee(${id})"
                >
                  Detail
                </label>
              </td>
            </tr>`;
};

const getStock = async () => {
  const stockWrapper = document.getElementById("table-stock-wrapper");
  stockWrapper.inner = "";
  const res = await fetch(`/api/employee/stock-leave`);
  const data = res.json();
  const json = await data;
  const { stock_available } = json;

  stockWrapper.innerHTML = tableLeaveStock(stock_available);
};

const tableLeaveStock = (stock_available) => {
  let badgeBg = "bg-danger";

  if (stock_available <= 5) {
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

const detailEmployee = async (id) => {
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
