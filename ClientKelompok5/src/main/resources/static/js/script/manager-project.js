const URL = "/api/project";

const tableContent = (no, id, name, status, members) => {
  return `<tr>
              <td>${no}</td>
              <td>${name}</td>
              <td>
                <span class="badge bg-gradient ${
                  status === true ? "bg-primary" : "bg-success"
                }"
                  >${status === true ? "Finish" : "Running"}</span
                >
              </td>
              <td>${members === 0 ? "-" : members}</td>
              <td>
                <button
                  class="btn btn-sm bg-gradient btn-dark"
                  data-bs-toggle="modal"
                  data-bs-target="#modalCheckMember"
                  onclick="checkMember(${id})"
                >
                  <i class="mdi mdi-eye"></i>
                  Check Member
                </button>
                <button
                  class="btn btn-sm bg-gradient btn-primary bg-cyan border-0"
                  data-bs-toggle="modal"
                  data-bs-target="#modalAddMember"
                  onclick="beforeAddMember()"
                >
                  <i class="mdi mdi-plus"></i>
                  Add Member
                </button>
                <span
                  class="btn btn-sm bg-gradient btn-orange text-white"
                  data-bs-toggle="modal"
                  data-bs-target="#modalEditProject"
                  onclick="beforeEditProject(${id})"
                >
                  <i class="mdi mdi-account-edit"></i>
                  Edit
                </span>
                <span
                  class="btn btn-sm bg-gradient btn-danger text-white"
                  data-bs-toggle="modal"
                  data-bs-target="#modalDeleteProject"
                  onclick="deleteProject(${id})"
                  ><i class="mdi mdi-delete"></i>
                  Delete
                </span>
              </td>
            </tr>`;
};

const getAllProject = () => {
  $.ajax({
    url: URL,
    method: "GET",
    dataType: "JSON",
    contentType: "application/json",
    success: (results) => {
      console.log(results);
      const tableWrapper = document.getElementById("table-wrapper");
      let i = 0;
      results.forEach((e) => {
        i += 1;
        tableWrapper.innerHTML += tableContent(
          i,
          e.id,
          e.name,
          e.status,
          e.members
        );
      });
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

const checkMember = () => {
  console.log("Check Member");
};

const beforeEditProject = (id) => {
  $.ajax({
    url: `${URL}/${id}`,
    method: "GET",
    dataType: "JSON",
    contentType: "application/json",
    success: (result) => {
      const { name, status } = result;
      $("#edit-project-name").val(name);
      if (status === true) {
        document.getElementById("isFinished").setAttribute("checked", "");
        document.getElementById("text-status").value = "Selesai";
      } else {
        document.getElementById("isFinished").removeAttribute("checked");
        document.getElementById("text-status").value = "Belum selesai";
      }
      /*$("#isFinished").on("change", function () {
        if ($(this).is(":checked")) {
          console.log("finish");
          $("#text-status").val("Finish");
        } else {
          console.log("on going");
          $("#text-status").val("On Going");
        }
      });*/
      $("#project-edit-id").val(id);
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

const editProject = () => {
  const id = $("#project-edit-id").val();
  const status = $("#isFinished").is(":checked");
  const name = $("#edit-project-name").val();
  console.log({ id, status, name });
  $.ajax({
    url: URL + "/" + id,
    method: "PUT",
    dataType: "JSON",
    beforeSend: addCsrfToken(),
    data: JSON.stringify({
      name,
      status,
    }),
    contentType: "application/json",
    success: (result) => {
      Swal.fire("Saved!", "", "success").then(
        (r) => (window.location.href = "")
      );
      console.log(result);
      $("#modalEditProject").modal("hide");
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

const beforeAddMember = () => {};

const loadPage = () => {
  getAllProject();
  const triggerAddProject = document.getElementById("trigger-add-project");
  const triggerAddMember = document.getElementById(
    "trigger-add-member-project"
  );
  const triggerDeleteProject = document.getElementById(
    "trigger-delete-project"
  );
  const triggerUpdateProject = document.getElementById("trigger-edit-project");
  // add project
  triggerAddProject.addEventListener("click", () => {
    document.getElementById("submit-project").addEventListener("click", () => {
      const name = document.getElementById("input-project-name").value;
      $.ajax({
        url: URL,
        method: "POST",
        dataType: "JSON",
        data: JSON.stringify({
          name,
        }),
        contentType: "application/json",
        success: (result) => {
          console.log(result);
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
  });

  // add member
  triggerAddMember.addEventListener("click", () => {});

  // update project
  triggerUpdateProject.addEventListener("click", () => {});

  // delete project
  triggerDeleteProject.addEventListener("click", () => {
    deleteProject(1);
  });
};

const deleteProject = (id) => {
  const swalWithBootstrapButtons = Swal.mixin({
    customClass: {
      confirmButton: "btn btn-success",
      cancelButton: "btn btn-danger",
    },
    buttonsStyling: true,
  });

  swalWithBootstrapButtons
    .fire({
      title: "Are you sure?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Yes, delete it!",
      cancelButtonText: "No, cancel!",
      reverseButtons: true,
    })
    .then((result) => {
      if (result.isConfirmed) {
        $.ajax({
          url: `${URL}/${id}`,
          method: "DELETE",
          beforeSend: addCsrfToken(),
          contentType: "application/json",
          success: (result) => {
            swalWithBootstrapButtons
              .fire("Deleted!", "Your file has been deleted.", "success")
              .then((r) => (window.location.href = ""));
          },
          error: function (xhr, ajaxOptions, thrownError) {
            Swal.fire({
              icon: "error",
              title: "Oops...",
              text: "Something went wrong!",
            }).then((result) => {
              if (result.isConfirmed) {
              }
            });
          },
        });
      } else if (
        /* Read more about handling dismissals below */
        result.dismiss === Swal.DismissReason.cancel
      ) {
        swalWithBootstrapButtons.fire(
          "Cancelled",
          "Your imaginary file is safe :)",
          "error"
        );
      }
    });
};
window.addEventListener("DOMContentLoaded", loadPage);
