// Url api
const URL = "http://localhost:8081/api/employee";
const triggerAddBtn = document.getElementById("trigger-add-button");
const triggerDetail = document.getElementById("trigger-detail");
const triggerEdit = document.getElementById("trigger-edit");
const triggerDelete = document.getElementById("trigger-delete");

const loadData = async () => {
  triggerAddBtn.addEventListener("click", () => {
    const inputSubmit = document.getElementById("input-submit");
    const selectRole = document.getElementById("input-role");
    const inputManager = document.getElementById("input-manager");
    selectRole.addEventListener("change", (event) => {
      const value = event.target.value;
      if (value === "1") {
        inputManager.removeAttribute("disabled");
      } else {
        inputManager.setAttribute("disabled", "");
      }
    });
    inputSubmit.addEventListener("click", postData);
  });
  triggerDetail.addEventListener("click", detailEmployee);
  triggerEdit.addEventListener("click", beforeUpdate);
  triggerDelete.addEventListener("click", deleteEmployee);
};

const postData = async () => {
  const first_name = $("#input-fName").val();
  const last_name = $("#input-lName").val();
  const email = $("#input-email").val();
  const phone_number = $("#input-phone").val();
  const role_id = $("#input-role").find(":selected").val();
  const manager_id = $("#input-manager").find(":selected").val();
  await submitData({
    first_name,
    last_name,
    email,
    phone_number,
    role_id,
    manager_id,
  });
};

const submitData = ({
  first_name,
  last_name,
  email,
  phone_number,
  role_id,
  manager_id,
}) => {
  $.ajax({
    url: URL,
    method: "POST",
    dataType: "JSON",
    data: JSON.stringify({
      first_name,
      last_name,
      email,
      phone_number,
      role_id,
      manager_id,
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
};
const generateOption = (id, name, isSelected) => {
  const option = document.createElement("option");
  option.value = id;
  option.text = name;
  option.selected = isSelected;
  return option;
};
const detailEmployee = (id) => {
  $("#detail-fName").val("My First Name");
  $("#detail-lName").val("My Last Name");
  $("#detail-email").val("myemail@gmail.com");
  $("#detail-phone").val("089172917391");
  document.getElementById("detail-role").add(generateOption(1, "User", true));
  document
    .getElementById("detail-manager")
    .add(generateOption(1, "Mamang", true));
  /*$.ajax({
    url: `${URL}/${id}`,
    method: "GET",
    contentType: "application/json",
    success: (result) => {

      console.log(result);
    },
    error: function (xhr, ajaxOptions, thrownError) {
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: "Something went wrong!",
      }).then((result) => {
        if (result.isConfirmed) {
          window.location.href = "";
        }
      });
    },
  });*/
};

const beforeUpdate = (id) => {
  $.ajax({
    url: `${URL}/${id}`,
    method: "GET",
    contentType: "application/json",
    success: (result) => {
      $("#edit-fName").val(result.first_name);
      $("#edit-lName").val(result.last_name);
      $("#edit-email").val(result.email);
      $("#edit-phone").val(result.phone_number);
      document.getElementById("edit-role").add(generateOption(1, "User", true));
      document
        .getElementById("edit-manager")
        .add(generateOption(1, "Mamang", true));
    },
    error: function (xhr, ajaxOptions, thrownError) {
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: "Something went wrong!",
      }).then((result) => {
        /*if (result.isConfirmed) {
          window.location.href = "";
        }*/
      });
    },
  });

  document
    .getElementById("submit-update")
    .addEventListener("click", updateEmployee);
};
const updateEmployee = () => {
  const first_name = $("#edit-fName").val();
  const last_name = $("#edit-lName").val();
  const email = $("#edit-email").val();
  const phone_number = $("#edit-phone").val();
  const role_id = $("#edit-role").find(":selected").val();
  const manager_id = $("#edit-manager").find(":selected").val();
  $.ajax({
    url: URL,
    method: "POST",
    dataType: "JSON",
    data: JSON.stringify({
      first_name,
      last_name,
      email,
      phone_number,
      role_id,
      manager_id,
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
};

const deleteEmployee = (id) => {
  const swalWithBootstrapButtons = Swal.mixin({
    customClass: {
      confirmButton: "btn btn-success",
      cancelButton: "btn btn-danger",
    },
    buttonsStyling: true,
  });

  swalWithBootstrapButtons
    .fire({
      title: "Are you sure?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Yes, delete it!",
      cancelButtonText: "No, cancel!",
      reverseButtons: true,
    })
    .then((result) => {
      if (result.isConfirmed) {
        $.ajax({
          url: `${URL}/${id}`,
          method: "DELETE",
          contentType: "application/json",
          success: (result) => {
            swalWithBootstrapButtons.fire(
              "Deleted!",
              "Your file has been deleted.",
              "success"
            );
          },
          error: function (xhr, ajaxOptions, thrownError) {
            Swal.fire({
              icon: "error",
              title: "Oops...",
              text: "Something went wrong!",
            }).then((result) => {
              if (result.isConfirmed) {
                window.location.href = "";
              }
            });
          },
        });
      } else if (
        /* Read more about handling dismissals below */
        result.dismiss === Swal.DismissReason.cancel
      ) {
        swalWithBootstrapButtons.fire(
          "Cancelled",
          "Your imaginary file is safe :)",
          "error"
        );
      }
    });
};

window.addEventListener("load", loadData);
