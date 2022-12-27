const URL = "http://localhost:8081/api/permission";
const loadDataPage = () => {
  const triggerDetailProject = document.getElementById(
    "trigger-detail-project"
  );
  const triggerUpdateProject = document.getElementById(
    "trigger-update-project"
  );
  triggerDetailProject.addEventListener("click", () => {});
  triggerUpdateProject.addEventListener("click", () => {
    const updateStatus = document.getElementById("update-status");
    const submitUpdateStatus = document.getElementById("submit-update");
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

document.addEventListener("DOMContentLoaded", loadDataPage);
