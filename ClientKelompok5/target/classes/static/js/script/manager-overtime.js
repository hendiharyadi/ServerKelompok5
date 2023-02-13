const URL = "/api/overtime/manager";
const notFoundWrapper = document.getElementById("data-not-found");
const tableContentWrapper = document.getElementById("table-content");
const loadManagerOvertime = async () => {
  await loadDataTable();
};
const loadDataTable = async () => {
  const tableWrapper = document.getElementById("table-wrapper");
  tableWrapper.innerHTML = "";
  try {
    const response = await fetch(URL);
    const json = await response.json();
    console.log(json);
    let i = 0;
    if (json.length !== 0) {
      tableContentWrapper.classList.remove("d-none");
      notFoundWrapper.classList.add("d-none");
      json
        .sort((a, b) => b.id - a.id)
        .forEach((o) => {
          i += 1;
          let classStatus = "";
          if (o.status === "PENDING") {
            classStatus = "bg-warning";
          } else if (o.status === "APPROVED") {
            classStatus = "bg-success";
          } else {
            classStatus = "bg-danger";
          }

          tableWrapper.innerHTML += ` <tr>
                          <td>${i}</td>
                          <td>${o.employee.first_name}</td>
                          <td>${moment(o.start_overtime).format("lll")}</td>
                          <td>${moment(o.end_overtime).format("lll")}</td>
                          <td>${o.project.name}</td>
                          <td>
                            <label class="badge ${classStatus}">${
            o.status
          }</label>
                          </td>
                          <td>
                            <button
                              class="btn btn-sm btn-primary"
                              data-bs-toggle="modal"
                              data-bs-target="#modalDetailOvertime"
                              onclick="detailOvertime(${o.id})"
                                                          >
                              <i class="mdi mdi-looks"></i>
                              Detail
                            </button>
                            <button
                              class="btn btn-sm btn-orange text-white ${
                                o.status !== "PENDING" ? "d-none" : ""
                              }"
                              data-bs-toggle="modal"
                              data-bs-target="#modalUpdateOvertime"
                              onclick="preUpdateOvertime(${o.id}, ${
            o.project.id
          })"
               
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
  }
};
const preUpdateOvertime = (id, project_id) => {
  const submitUpdate = document.getElementById("submit-update");
  const btnSpinner = document.getElementById("spinner-button");

  submitUpdate.addEventListener("click", () => {
    btnSpinner.classList.remove("d-none");
    submitUpdate.classList.add("d-none");
    const updateStatus = $("#update-status").find(":selected").val();
    $.ajax({
      url: "/api/overtime/" + id,
      method: "PUT",
      dataType: "JSON",
      beforeSend: addCsrfToken(),
      data: JSON.stringify({
        start_overtime: "",
        end_overtime: "",
        project_id,
        note: "",
        status: updateStatus === "1",
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
        submitUpdate.classList.remove("d-none");
        await loadDataTable();
        $("#modalUpdateOvertime").modal("hide");
      },
      error: function (xhr, ajaxOptions, thrownError) {
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Something went wrong!",
        });
        btnSpinner.classList.add("d-none");
        submitUpdate.classList.remove("d-none");
        console.log({ xhr, ajaxOptions, thrownError });
      },
    });
  });
};

const detailOvertime = async (id) => {
  try {
    const res = await fetch(`/api/overtime/${id}`);
    const json = await res.json();
    $("#detail-overtime-date-start").val(json.start_overtime);
    $("#detail-overtime-date-end").val(json.end_overtime);
    document.getElementById("detail-overtime-note").textContent = json.note;
  } catch (e) {
    console.log(e);
  }
};

document.addEventListener("DOMContentLoaded", loadManagerOvertime);
