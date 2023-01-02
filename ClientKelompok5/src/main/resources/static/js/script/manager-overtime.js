const URL = "/api/overtime/manager";

const loadManagerOvertime = async () => {
  const tableWrapper = document.getElementById("table-wrapper");
  try {
    const response = await fetch(URL);
    const json = await response.json();
    console.log(json);
    let i = 0;
    json.forEach((o) => {
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
                          <td>${o.start_overtime}</td>
                          <td>${o.end_overtime}</td>
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
  } catch (e) {
    console.log(e);
  }
};

const x = {
  note: "",
  start_overtime: "",
  end_overtime: "",
  project_id: 2,
  status: false,
};

const preUpdateOvertime = (id, project_id) => {
  document.getElementById("submit-update").addEventListener("click", () => {
    const updateStatus = $("#update-status").find(":selected").val();
    /* console.log({
      start_overtime: "",
      end_overtime: "",
      project_id,
      note: "",
      status: updateStatus === "1",
    });*/
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
