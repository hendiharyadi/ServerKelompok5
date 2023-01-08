const URL = "/api/project";

let employees = [];
let members = [];

const URL_MEMBERS = "/api/employee/dashboard";

const notFoundWrapper = document.getElementById("data-not-found");
const tableContentWrapper = document.getElementById("table-content");

const tableContent = (no, id, name, start, end, status, members) => {
  let statusProject = false;
  const currentdate = new Date();
  const fullYear = currentdate.getFullYear();
  const month = currentdate.getMonth() + 1;
  const date = currentdate.getDate();
  const dateNow = `${fullYear}-${month < 10 ? "0" + month : month}-${
    date < 10 ? "0" + date : date
  }`.trim();

  statusProject = dateNow === end;
  return `<tr>
              <td>${no}</td>
              <td>${name}</td>
              <td>${start}</td>
              <td>${end}</td>
              <td>
                <span class="badge bg-gradient ${
                  statusProject === true ? "bg-success" : "bg-warning"
                }"
                  >${statusProject === true ? "Finish" : "Running"}</span
                >
              </td>
              <td>${members.length === 0 ? "-" : members.length}</td>
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
               
              </td>
            </tr>`;
};

/*<span
    className="btn btn-sm bg-gradient btn-danger text-white"
    data-bs-toggle="modal"
    data-bs-target="#modalDeleteProject"
    onClick="deleteProject(${id})"
><i className="mdi mdi-delete"></i>
                  Delete
</span>*/

const generateOption = (id, name, isSelected) => {
  const option = document.createElement("option");
  option.value = id;
  option.text = name;
  option.selected = isSelected;
  return option;
};

const beforeAddProject = async () => {
  const dateNow = currentDate();
  const selectMembers = document.getElementById("select-members");
  selectMembers.inner = "";
  $("#input-date-start").attr("min", dateNow);
  $("#input-date-end").attr("min", dateNow);

  try {
    const res = await fetch(URL_MEMBERS);
    const json = await res.json();
    employees = json.managers.map((e) => {
      return { id: e.id, name: e.first_name, status: false };
    });
    employees.forEach((e) => {
      selectMembers.add(generateOption(e.id, e.name, false));
    });
    $("#select-members").select2({
      placeholder: "Select members",
      allowClear: true,
    });
  } catch (e) {
    console.log(e);
  }
};

const updateMember = (id, status) => {
  const index = employees.findIndex((e) => e.id === id);
  employees = employees.map((e) => {
    if (e.id === id) {
      e.status = !status;
    }
    return e;
  });
  /*employees.forEach((e) => {
    if (e.status) {
      members.push(e.id);
    }
  });*/
  members = employees
    .filter((e) => e.status === true)
    .map((e) => {
      return e.id;
    });
  loadTableMember();
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

const getAllProject = () => {
  $.ajax({
    url: URL,
    method: "GET",
    dataType: "JSON",
    contentType: "application/json",
    success: (results) => {
      const tableWrapper = document.getElementById("table-wrapper");
      let i = 0;
      tableWrapper.innerHTML = "";
      if (results.length !== 0) {
        tableContentWrapper.classList.remove("d-none");
        notFoundWrapper.classList.add("d-none");
        results.forEach((e) => {
          i += 1;
          tableWrapper.innerHTML += tableContent(
            i,
            e.id,
            e.name,
            e.start_project,
            e.end_project,
            e.status,
            e.members
          );
        });
      } else {
        tableContentWrapper.classList.add("d-none");
        notFoundWrapper.classList.remove("d-none");
      }
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
      text: "Fill in the data completely!",
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
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Saved!",
        showConfirmButton: false,
        timer: 1500,
      });
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
  const allSelect = $("#select-members").val();
  const name = document.getElementById("input-project-name").value;
  const btnSubmit = document.getElementById("submit-project");
  const btnSpinner = document.getElementById("spinner-button");

  const start_project = $("#input-date-start").val();
  const end_project = $("#input-date-end").val();

  if (name.trim() === "" || start_project === "" || end_project === "") {
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: "Fill in the data completely!",
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
      employees: allSelect,
    }),
    contentType: "application/json",
    success: (result) => {
      $("#input-date-start").val("");
      $("#input-date-end").val("");
      $("#input-project-name").val("");
      getAllProject();
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Saved!",
        showConfirmButton: false,
        timer: 1500,
      });
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
