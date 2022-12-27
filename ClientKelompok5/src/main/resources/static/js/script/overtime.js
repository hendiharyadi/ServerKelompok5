const triggerAddOvertime = document.getElementById("trigger-add-overtime");
const preSubmitData = () => {
  const data = {
    note: "semangat!",
    start_overtime: "2022-12-02 18.00",
    end_overtime: "2022-12-02 21.00",
    employee_id: 4,
    project_id: 2,
  };

  document.getElementById("submit-overtime").addEventListener("click", () => {
    const start_overtime = document.getElementById(
      "input-overtime-date-start"
    ).value;
    const end_overtime = document.getElementById(
      "input-overtime-date-end"
    ).value;
    const note = document.getElementById("input-note-overtime").value;
    const project_id = document.getElementById("input-select-project").value;

    $.ajax({
      url: URL,
      method: "POST",
      dataType: "JSON",
      data: JSON.stringify({
        start_overtime,
        end_overtime,
        note,
        project_id,
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
};
const loadedPage = () => {
  triggerAddOvertime.addEventListener("click", preSubmitData);
};
window.addEventListener("load", loadedPage);
