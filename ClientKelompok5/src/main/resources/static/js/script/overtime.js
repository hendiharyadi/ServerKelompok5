const URL = "/api/overtime";

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
    project_id.length === 1 ||
    end_overtime.length !== 0 ||
    start_overtime.length === 0 ||
    note.length === 0
  ) {
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: "Fill in all the data in this form",
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
      start_overtime,
      end_overtime,
      note,
      project_id,
    }),
    contentType: "application/json",
    success: async (result) => {
      console.log(result);
      Swal.fire("Saved!", "", "success");
      btnSpinner.classList.add("d-none");
      btnSubmit.classList.remove("d-none");
      $("#modalAddOvertime").modal("hide");
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
    json.forEach((p) => {
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
  } catch (e) {
    console.log(e);
  }
};
const loadedPage = async () => {
  await loadData();
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
  return ` <tr>
              <td>${no}</td>
              <td>${start_date}</td>
              <td>${end_date}</td>
              <td>${project_name}</td>
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
                  onclick="detailOvertime(${id})"
                >
                  Detail
                </label>
              </td>
            </tr>`;
};
window.addEventListener("load", loadedPage);
