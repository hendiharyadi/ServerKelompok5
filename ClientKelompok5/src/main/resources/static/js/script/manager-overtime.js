const URL = "";
const loadManagerOvertime = () => {
  const triggerDetailOvertime = document.getElementById(
    "triggerDetailOvertime"
  );
  const triggerUpdateOvertime = document.getElementById(
    "triggerUpdateOvertime"
  );

  triggerDetailOvertime.addEventListener("click", () => {
    const DOFname = document.getElementById("detail-overtime-fName");
    const DOLname = document.getElementById("detail-overtime-lName");
    const DOEmail = document.getElementById("detail-overtime-email");
    const DOPhone = document.getElementById("detail-overtime-phone");
    const DOStart = document.getElementById("detail-overtime-date-start");
    const DOEnd = document.getElementById("detail-overtime-date-end");
    const DONote = document.getElementById("detail-overtime-note");
    DOFname.value = "First Name";
    DOLname.value = "Last Name";
    DOEmail.value = "myemail@gmail.com";
    DOPhone.value = "0192091031";
    DOStart.value = "2022-12-08 20:00";
    DOEnd.value = "2022-12-08 20:00";
    DONote.value =
      "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Deserunt, expedita!";
  });

  triggerUpdateOvertime.addEventListener("click", () => {
    const updateStatus = document.getElementById("update-status-overtime");
    const submitUpdateStatus = document.getElementById(
      "submit-update-overtime"
    );
    updateStatus.add(generateOption(1, "Running", true));
    updateStatus.add(generateOption(2, "Finished", false));

    submitUpdateStatus.addEventListener("click", () => {
      $.ajax({
        url: URL,
        method: "POST",
        dataType: "JSON",
        data: JSON.stringify({
          status: submitUpdateStatus.value,
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
  });
};

const generateOption = (id, name, isSelected) => {
  const option = document.createElement("option");
  option.value = id;
  option.text = name;
  option.selected = isSelected;
  return option;
};

document.addEventListener("DOMContentLoaded", loadManagerOvertime);
