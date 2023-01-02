const URL = "/api/overtime";

const triggerAddOvertime = document.getElementById("trigger-add-overtime");
const submitData = () => {
  const start_overtime = document.getElementById(
    "input-overtime-date-start"
  ).value;
  const end_overtime = document.getElementById("input-overtime-date-end").value;
  const note = document.getElementById("input-note-overtime").value;
  const project_id = document.getElementById("input-select-project").value;

  console.log({ start_overtime, end_overtime, note, project_id });
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
    success: (result) => {
      console.log(result);
      Swal.fire("Saved!", "", "success").then(
        (r) => (window.location.href = "")
      );
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
const loadedPage = async () => {
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
  triggerAddOvertime.addEventListener("click", preSubmitData);
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
