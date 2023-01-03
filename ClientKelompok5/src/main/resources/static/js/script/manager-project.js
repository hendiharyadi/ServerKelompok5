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
              <td>${members === 0 ? "-" : members.length}</td>
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
      tableWrapper.innerHTML = "";
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

const checkMember = async (id) => {
  try {
    const res = await fetch(`${URL}/members/${id}`);
    const json = await res.json();
    const tableMember = document.getElementById("table-member");
    tableMember.innerHTML = "";
    console.log(json);
    let i = 0;
    json.forEach((m) => {
      i += 1;
      tableMember.innerHTML += `<tr>
                      <th scope="row">${i}</th>
                      <td>${m.first_name}</td>
                      <td>${m.email}</td>
                    </tr>`;
    });
  } catch (e) {
    console.log(e);
  }
};

const beforeEditProject = (id) => {
  $.ajax({
    url: `${URL}/${id}`,
    method: "GET",
    dataType: "JSON",
    contentType: "application/json",
    success: (result) => {
      $("#edit-project-name").val(result.name);
      /*$("#edit-date-start").val(result.start_project);
      $("#edit-date-end").val(result.end_project);*/
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
  const name = $("#edit-project-name").val();
  const btnSubmit = document.getElementById("edit-project");
  const btnSpinner = document.getElementById("spinner-button-edit");

  if (name.trim() === "") {
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: "Fill in all the data in this form",
    });
    return;
  }

  btnSubmit.classList.add("d-none");
  btnSpinner.classList.remove("d-none");
  $.ajax({
    url: URL + "/" + id,
    method: "PUT",
    dataType: "JSON",
    beforeSend: addCsrfToken(),
    data: JSON.stringify({
      name,
    }),
    contentType: "application/json",
    success: (result) => {
      getAllProject();
      btnSubmit.classList.remove("d-none");
      btnSpinner.classList.add("d-none");
      Swal.fire("Saved!", "", "success");
      console.log(result);
      $("#modalEditProject").modal("hide");
    },
    error: function (xhr, ajaxOptions, thrownError) {
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: "Something went wrong!",
      });
      btnSubmit.classList.remove("d-none");
      btnSpinner.classList.add("d-none");
      console.log({ xhr, ajaxOptions, thrownError });
    },
  });
};

const loadPage = () => {
  getAllProject();
};

const createProject = () => {
  const name = document.getElementById("input-project-name").value;
  const btnSubmit = document.getElementById("submit-project");
  const btnSpinner = document.getElementById("spinner-button");

  const start_project = $("#input-date-start").val();
  const end_project = $("#input-date-end").val();

  if (name.trim() === "" || start_project === "" || end_project === "") {
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: "Fill in all the data in this form",
    });
    return;
  }

  btnSubmit.classList.add("d-none");
  btnSpinner.classList.remove("d-none");

  $.ajax({
    url: URL,
    method: "POST",
    dataType: "JSON",
    beforeSend: addCsrfToken(),
    data: JSON.stringify({
      name,
      start_project,
      end_project,
    }),
    contentType: "application/json",
    success: (result) => {
      $("#input-date-start").val("");
      $("#input-date-end").val("");
      $("#input-project-name").val("");
      getAllProject();
      Swal.fire("Saved!", "", "success");
      console.log(result);
      btnSubmit.classList.remove("d-none");
      btnSpinner.classList.add("d-none");
      $("#modalAddProject").modal("hide");
    },
    error: function (xhr, ajaxOptions, thrownError) {
      btnSubmit.classList.remove("d-none");
      btnSpinner.classList.add("d-none");
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: "Something went wrong!",
      });
      console.log({ xhr, ajaxOptions, thrownError });
    },
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
            getAllProject();
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
