const URL = "/api/history/overtime";
const notFoundWrapper = document.getElementById("data-not-found");
const tableContentWrapper = document.getElementById("table-content");
const onLoadPage = async () => {
  try {
    const res = await fetch(URL);
    const json = await res.json();
    const tableWrapper = document.getElementById("table-wrapper");
    let i = 0;
    if (json.length !== 0) {
      tableContentWrapper.classList.remove("d-none");
      notFoundWrapper.classList.add("d-none");
      json.forEach((e) => {
        i += 1;
        const date = new Date(e.date_history);
        tableWrapper.innerHTML += tableContent(
          i,
          e.id,
          `${new Date(e.date_history).toDateString()} | ${new Date(
            e.date_history
          ).toLocaleTimeString()}`,
          new Date(e.overtime.start_overtime).toLocaleString(),
          new Date(e.overtime.end_overtime).toLocaleString(),
          e.overtime.status
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

const detailOvertimeHistory = async (id) => {
  try {
    const res = await fetch(`${URL}/${id}`);
    const json = await res.json();
    $("#detail-updatedAt").val(json.date_history);
    $("#detail-date-start").val(json.overtime.start_overtime);
    $("#detail-date-end").val(json.overtime.end_overtime);
    document.getElementById("detailNote").textContent = json.overtime.note;
  } catch (e) {
    console.log(e);
  }
};

const tableContent = (no, id, updated_at, start, end, status) => {
  let classStatus = "";
  if (status === "PENDING") {
    classStatus = "bg-warning";
  } else if (status === "APPROVED") {
    classStatus = "bg-success";
  } else if (status === "REJECTED") {
    classStatus = "bg-danger";
  }
  return ` <tr>
              <td>${no}</td>
              <td>${updated_at}</td>
              <td>${start}</td>
              <td>${end}</td>
              <td>
                <label class="badge ${classStatus}">${status}</label>
              </td>
              <td>
                <label
                  class="text-primary pointer"
                  data-bs-toggle="modal"
                  data-bs-target="#modalDetailLeave"
                  onclick="detailOvertimeHistory(${id})"
                >
                  Detail
                </label>
              </td>
            </tr>`;
};

window.addEventListener("DOMContentLoaded", onLoadPage);
