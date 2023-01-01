const URL = "/api/history/overtime";

const onLoadPage = async () => {
  try {
    const res = await fetch(URL);
    const json = await res.json();
    const tableWrapper = document.getElementById("table-wrapper");
    json.forEach(
      (e) =>
        (tableWrapper.innerHTML += tableContent(
          e.id,
          e.id,
          e.date_history,
          e.overtime.status
        ))
    );
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
    console.log(json);
  } catch (e) {
    console.log(e);
  }
};

const tableContent = (no, id, updated_at, status) => {
  return ` <tr>
              <td>${no}</td>
              <td>${updated_at}</td>
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
                  onclick="detailOvertimeHistory(${id})"
                >
                  Detail
                </label>
              </td>
            </tr>`;
};

window.addEventListener("DOMContentLoaded", onLoadPage);
