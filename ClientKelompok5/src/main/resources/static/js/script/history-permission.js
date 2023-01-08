const URL = "/api/history/permission";
const notFoundWrapper = document.getElementById("data-not-found");
const tableContentWrapper = document.getElementById("table-content");
const loadPage = async () => {
  const tableWrapper = document.getElementById("table-wrapper");
  try {
    const res = await fetch(URL);
    const json = await res.json();
    let i = 0;
    if (json.length !== 0) {
      tableContentWrapper.classList.remove("d-none");
      notFoundWrapper.classList.add("d-none");
      json
        .sort((a, b) => b.id - a.id)
        .forEach((e) => {
          i += 1;
          tableWrapper.innerHTML += tableContent(
            i,
            e.id,
            e.permission.leave_type,
            moment(e.date_history).format("lll"),
            moment(e.permission.start_leave).format("ll"),
            moment(e.permission.end_leave).format("ll"),
            e.status
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

const detailHistoryRequest = async (id) => {
  const leaveDetail = document.getElementById("detail-leaveType");
  const detailStartDate = document.getElementById("detail-date-start");
  const detailEndDate = document.getElementById("detail-date-end");
  const detailNote = document.getElementById("detail-note");
  try {
    const res = await fetch(`/api/permission/${id}`);
    const json = await res.json();
    leaveDetail.value = json.leave_type;
    detailStartDate.value = json.start_leave;
    detailEndDate.value = json.end_leave;
    detailNote.innerText = json.note;
  } catch (e) {
    console.log(e);
  }
};

const tableContent = (
  no,
  id,
  leave_type,
  date_history,
  start_date,
  end_date,
  status
) => {
  let classStatus = "";
  if (status === "PENDING") {
    classStatus = "bg-warning";
  } else if (status === "APPROVED") {
    classStatus = "bg-success";
  } else if (status === "REJECTED") {
    classStatus = "bg-danger";
  }
  console.log(status);
  return ` <tr>
              <td>${no}</td>
              <td>${leave_type}</td>
              <td>${date_history}</td>
              <td>${start_date}</td>
              <td>${end_date}</td>
              <td>
                <span class="badge ${classStatus}">${status}</span>
              </td>
            </tr>`;
};

window.addEventListener("DOMContentLoaded", loadPage);
