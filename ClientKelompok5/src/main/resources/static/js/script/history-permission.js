const URL = "/api/history/permission";

const loadPage = async () => {
  const tableWrapper = document.getElementById("table-wrapper");
  try {
    const res = await fetch(URL);
    const json = await res.json();
    console.log(json);
    let i = 0;
    json.forEach((e) => {
      i += 1;
      tableWrapper.innerHTML += tableContent(
        i,
        e.id,
        e.permission.leave_type,
        e.permission.start_leave,
        e.permission.end_leave,
        e.permission.status
      );
    });
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

const tableContent = (no, id, leave_type, start_date, end_date, status) => {
  return ` <tr>
              <td>${no}</td>
              <td>${leave_type}</td>
              <td>${start_date}</td>
              <td>${end_date}</td>
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
                  onclick="detailHistoryRequest(${id})"
                >
                  Detail
                </label>
              </td>
            </tr>`;
};

window.addEventListener("DOMContentLoaded", loadPage);
