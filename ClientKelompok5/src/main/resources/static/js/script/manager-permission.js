const URL = "/api/permission/manager";
const notFoundWrapper = document.getElementById("data-not-found");
const tableContentWrapper = document.getElementById("table-content");

const loadDataPage = async () => {
  await loadDataTable();
};

const loadDataTable = async () => {
  try {
    const res = await fetch(URL);
    const json = await res.json();
    const tableWrapper = document.getElementById("table-wrapper");
    tableWrapper.innerHTML = "";
    let i = 0;
    if (json.length !== 0) {
      tableContentWrapper.classList.remove("d-none");
      notFoundWrapper.classList.add("d-none");
      json
        .sort((a, b) => b.id - a.id)
        .forEach((e) => {
          i += 1;
          let status = false;
          let classStatus = "";
          if (e.status === "PENDING") {
            classStatus = "bg-warning";
          } else if (e.status === "APPROVED") {
            status = true;
            classStatus = "bg-success";
          } else if (e.status === "REJECTED") {
            classStatus = "bg-danger";
          }
          const type = e.leave_type === "CUTI" ? 1 : 2;
          const total_day = days(
            new Date(e.end_leave).getTime(),
            new Date(e.start_leave).getTime()
          );
          tableWrapper.innerHTML += `  <tr>
                          <td>${i}</td>
                          <td>${e.employee.first_name}</td>
                          <td>${e.leave_type}</td>
                          <td>${moment(e.start_leave).format("ll")}</td>
                          <td>${moment(e.end_leave).format("ll")}</td>
                          <td>
                            <label class="badge ${classStatus}">${
            e.status
          }</label>
                          </td>
                          <td>
                            <button
                              class="btn btn-sm btn-primary"
                              data-bs-toggle="modal"
                              data-bs-target="#modalDetailLeave"
                             onclick="detailPermission(${e.id})"
                            >
                              <i class="mdi mdi-looks"></i>
                              Detail
                            </button>
                            <button
                              class="btn btn-sm btn-orange text-white ${
                                e.status !== "PENDING" ? "d-none" : ""
                              }"
                              data-bs-toggle="modal"
                              data-bs-target="#modalUpdateLeave"
                              onclick="preUpdatePermission(${e.id}, ${type}, ${
            e.employee.id
          },${total_day})"
                            >
                              <i class="mdi mdi-account-edit"></i>
                              Update
                            </button>
                          </td>
                        </tr>`;
        });
    } else {
      tableContentWrapper.classList.add("d-none");
      notFoundWrapper.classList.remove("d-none");
    }
  } catch (e) {
    console.log(e);
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: "Failed to load data!",
    });
  }
};

const detailPermission = async (id) => {
  try {
    const response = await fetch(`/api/permission/${id}`);
    if (!response.ok) {
      Swal.fire({
        icon: "error",
        title: "Not Found",
        text: "Failed to load data permission!",
      });
    }
    const data = await response.json();
    $("#detail-permission-type").val(data.leave_type);
    $("#detail-date-start").val(data.start_leave);
    $("#detail-date-end").val(data.end_leave);
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
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: "Something went wrong!",
    });
  }
};

const days = (date_1, date_2) => {
  let difference = date_1 - date_2;
  let TotalDays = Math.ceil(difference / (1000 * 3600 * 24));
  return TotalDays;
};

const preUpdatePermission = (id, leave_type, employee_id, total_day) => {
  // console.log({ id, leave_type, employee_id, start_leave, end_leave });
  $("#leave-type").val(leave_type);
  $("#request-id").val(id);
  $("#employee-id").val(employee_id);
  $("#total_day").val(total_day);
};

const getEmployeeStock = async (id) => {
  try {
    const res = await fetch(`/api/employee/${id}`);
    return await res.json();
  } catch (e) {
    console.log(e);
  }
};

const updatePermission = async () => {
  const btnSpinner = document.getElementById("spinner-button");
  const btnUpdate = document.getElementById("update-data");

  const updateStatus = $("#update-status").find(":selected").val();
  const leave_type = $("#leave-type").val();
  const employee_id = $("#employee-id").val();
  const leave_day = $("#total_day").val();
  const id = $("#request-id").val();
  const currentEmp = await getEmployeeStock(employee_id);
  const currentStockPermission = currentEmp.stockLeave.stock_available;
  console.log({ currentStockPermission, day: parseInt(leave_day) });

  if (updateStatus === "0") {
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: "Permission needs to be updated!",
    });
  }

  if (updateStatus === "1") {
    if (
      currentStockPermission > parseInt(leave_day) ||
      currentStockPermission === parseInt(leave_day)
    ) {
      btnSpinner.classList.remove("d-none");
      btnUpdate.classList.add("d-none");
      $.ajax({
        url: "/api/permission/" + id,
        method: "PUT",
        dataType: "JSON",
        beforeSend: addCsrfToken(),
        data: JSON.stringify({
          leave_type: leave_type === "1",
          start_leave: "",
          end_leave: "",
          note: "",
          status: true,
          employee_id,
          leave_day,
        }),
        contentType: "application/json",
        success: async (result) => {
          console.log(result);
          Swal.fire({
            position: "center",
            icon: "success",
            title: "Saved!",
            showConfirmButton: false,
            timer: 1500,
          });
          btnSpinner.classList.add("d-none");
          btnUpdate.classList.remove("d-none");
          $("#modalUpdateLeave").modal("hide");
          await loadDataTable();
        },
        error: function (xhr, ajaxOptions, thrownError) {
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Something went wrong!",
          });
          btnSpinner.classList.add("d-none");
          btnUpdate.classList.remove("d-none");
          $("#modalUpdateLeave").modal("hide");
          console.log({ xhr, ajaxOptions, thrownError });
        },
      });
    } else {
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: "The certain stock leave is insufficient!",
      });
    }
  } else {
    btnSpinner.classList.remove("d-none");
    btnUpdate.classList.add("d-none");
    $.ajax({
      url: "/api/permission/" + id,
      method: "PUT",
      dataType: "JSON",
      beforeSend: addCsrfToken(),
      data: JSON.stringify({
        leave_type: leave_type === "1",
        start_leave: "",
        end_leave: "",
        note: "",
        status: false,
        employee_id,
        leave_day,
      }),
      contentType: "application/json",
      success: async (result) => {
        console.log(result);
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Saved!",
          showConfirmButton: false,
          timer: 1500,
        });
        btnSpinner.classList.add("d-none");
        btnUpdate.classList.remove("d-none");
        $("#modalUpdateLeave").modal("hide");
        await loadDataTable();
      },
      error: function (xhr, ajaxOptions, thrownError) {
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Something went wrong!",
        });
        btnSpinner.classList.add("d-none");
        btnUpdate.classList.remove("d-none");
        $("#modalUpdateLeave").modal("hide");
        console.log({ xhr, ajaxOptions, thrownError });
      },
    });
  }
};

document.addEventListener("DOMContentLoaded", loadDataPage);
