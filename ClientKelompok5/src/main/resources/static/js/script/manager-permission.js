const URL = "/api/permission/manager";
const loadDataPage = async () => {
  try {
    const res = await fetch(URL);
    const json = await res.json();
    const tableWrapper = document.getElementById("table-wrapper");
    tableWrapper.innerHTML = "";
    let i = 0;
    json.forEach((e) => {
      i += 1;
      let status = false;
      let classStatus = "";
      if (e.status === "PENDING") {
        classStatus = "bg-warning";
      } else if (e.status === "APPROVED") {
        status = true;
        classStatus = "bg-success";
      } else {
        classStatus = "bg-danger";
      }
      tableWrapper.innerHTML += `  <tr>
                          <td>i</td>
                          <td>${e.employee.first_name}</td>
                          <td>${e.leave_type}</td>
                          <td>${e.start_leave}</td>
                          <td>${e.end_leave}</td>
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
                              onclick="preUpdatePermission(${e.id}, ${status})"
                            >
                              <i class="mdi mdi-account-edit"></i>
                              Update
                            </button>
                          </td>
                        </tr>`;
    });
  } catch (e) {
    console.log(e);
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
    /*const start = new Date(data.start_leave).toISOString().slice(0, 10);
    const end = new Date(data.end_leave).toISOString().slice(0, 10);*/
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
  }
};

const preUpdatePermission = (id, leave_type) => {
  document.getElementById("submit-update").addEventListener("click", () => {
    const updateStatus = $("#update-status").find(":selected").val();
    // console.log({ id, leave_type, status: updateStatus === "1" });
    $.ajax({
      url: "/api/permission/" + id,
      method: "PUT",
      dataType: "JSON",
      beforeSend: addCsrfToken(),
      data: JSON.stringify({
        leave_type,
        start_leave: "",
        end_leave: "",
        note: "",
        status: updateStatus === "1",
      }),
      contentType: "application/json",
      success: (result) => {
        console.log(result);
        Swal.fire("Saved!", "", "success").then(
          (e) => (window.location.href = "")
        );
        $("#modalUpdateLeave").modal("hide");
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
  });
};

document.addEventListener("DOMContentLoaded", loadDataPage);
