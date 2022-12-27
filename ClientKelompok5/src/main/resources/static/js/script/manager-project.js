const URL = "http://localhost:8081/api/project";

const loadPage = () => {
  const triggerAddProject = document.getElementById("trigger-add-project");
  const triggerDetailProject = document.getElementById("trigger-check-project");
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

  // detail project
  triggerDetailProject.addEventListener("click", () => {
    // fetch
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
          contentType: "application/json",
          success: (result) => {
            swalWithBootstrapButtons.fire(
              "Deleted!",
              "Your file has been deleted.",
              "success"
            );
          },
          error: function (xhr, ajaxOptions, thrownError) {
            Swal.fire({
              icon: "error",
              title: "Oops...",
              text: "Something went wrong!",
            }).then((result) => {
              if (result.isConfirmed) {
                window.location.href = "";
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
