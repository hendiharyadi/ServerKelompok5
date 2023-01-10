const URL = "/api/overtime";
const notFoundWrapper = document.getElementById("data-not-found");
const tableContentWrapper = document.getElementById("table-content");
let projects;
const submitData = () => {
  const btnSpinner = document.getElementById("spinner-button");
  const btnSubmit = document.getElementById("btn-submit");

  const start_overtime = document.getElementById(
    "input-overtime-date-start"
  ).value;
  const end_overtime = document.getElementById("input-overtime-date-end").value;
  const note = document.getElementById("input-note-overtime").value;
  const project_id = document.getElementById("input-select-project").value;

  if (
    project_id === "0" ||
    end_overtime.length === 0 ||
    start_overtime.length === 0 ||
    note.length === 0
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
      start_overtime: new Date(start_overtime).toLocaleString(),
      end_overtime: new Date(end_overtime).toLocaleString(),
      note,
      project_id,
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
      btnSubmit.classList.remove("d-none");
      $("#modalAddOvertime").modal("hide");
      $("#input-overtime-date-start").val("");
      $("#input-overtime-date-end").val("");
      $("#input-note-overtime").val("");
      project_id.value = "0";
      await loadData();
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

const loadData = async () => {
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
      json
        .sort((a, b) => b.id - a.id)
        .forEach((p) => {
          i += 1;
          tableWrapper.innerHTML += tableContent(
            i,
            p.id,
            p.start_overtime,
            p.end_overtime,
            p.project.name,
            p.status
          );
        });
    } else {
      tableContentWrapper.classList.add("d-none");
      notFoundWrapper.classList.remove("d-none");
    }
  } catch (e) {
    console.log(e);
  }
};
const loadedPage = async () => {
  await loadData();
};

const getCurrentDate = () => {
  const currentDate = new Date();

  const fullYear = currentDate.getFullYear();
  const month = currentDate.getMonth() + 1;
  const date = currentDate.getDate();
  return `${fullYear}-${month < 10 ? "0" + month : month}-${
    date < 10 ? "0" + date : date
  }`.trim();
};

const beforeAddOvertime = async () => {
  const today = new Date().toISOString().slice(0, 16);
  document.getElementsByName("start")[0].min = today;
  document.getElementsByName("end")[0].min = today;
  const selectProject = document.getElementById("input-select-project");
  selectProject.innerHTML = "";
  selectProject.add(generateOption(0, "Select Project", true));
  try {
    const res = await fetch("/api/employee/dashboard");
    const json = await res.json();
    const employeeProject = json.employeeProject;
    if (employeeProject.length === 0) {
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: "Can't apply for overtime, you're not working in a project!",
      }).then((e) => $("#modalAddOvertime").modal("hide"));
    }
    employeeProject.forEach((p) => {
      if (getCurrentDate() !== p.end_project) {
        selectProject.add(generateOption(p.id, p.name, false));
      }
    });
  } catch (e) {
    console.log(e);
  }

  $("#input-overtime-date-start").attr("min");
};

const detailOvertime = async (id) => {
  try {
    const res = await fetch(`${URL}/${id}`);
    const json = await res.json();

    $("#detail-date-start").val(json.start_overtime);
    $("#detail-date-end").val(json.end_overtime);
    document.getElementById("detail-note").value = json.note;
  } catch (e) {
    console.log(e);
  }
};

const tableContent = (no, id, start_date, end_date, project_name, status) => {
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
              <td>${moment(start_date).format("lll")}</td>
              <td>${moment(end_date).format("lll")}</td>
              <td>${project_name}</td>
              <td>
                <label class="badge ${classStatus}">${status}</label>
              </td>
              <td>
                <label
                  class="badge bg-primary text-white"
                  data-bs-toggle="modal"
                  data-bs-target="#modalDetailLeave"
                  onclick="detailOvertime(${id})"
                >
                 <i class="mdi mdi-looks"></i>
                  Detail
                </label>
              </td>
            </tr>`;
};

const generateOption = (id, name, isSelected) => {
  const option = document.createElement("option");
  option.value = id;
  option.text = name;
  option.selected = isSelected;
  return option;
};

window.addEventListener("load", loadedPage);
