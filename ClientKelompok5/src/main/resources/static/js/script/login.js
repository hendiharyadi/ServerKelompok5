function getCookie(name) {
  if (!document.cookie) {
    return null;
  }

  const xsrfCookies = document.cookie
    .split(";")
    .map((c) => c.trim())
    .filter((c) => c.startsWith(name + "="));

  if (xsrfCookies.length === 0) {
    return null;
  }
  return decodeURIComponent(xsrfCookies[0].split("=")[1]);
}

/*
const login = async () => {
  const url = "/api/auth";
  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;
  try {
    console.log({ username, password });
    const res = await fetch(url, {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json; charset=UTF-8",
        "X-CSRFToken": getCookie("CSRF-TOKEN"),
      },
      body: JSON.stringify({ username, password }),
    });
    const json = await res.json();
    console.log(json);
  } catch (e) {
    console.log(e);
  }
};
*/

const login = () => {
  const url = "/api/auth";
  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;
  $.ajax({
    url,
    method: "POST",
    dataType: "JSON",
    beforeSend: addCsrfToken(),
    data: JSON.stringify({
      username,
      password,
    }),
    contentType: "application/json",
    success: (result) => {
      // window.location.href = "/dashboard";
      console.log(result);
    },
    error: function (e) {
      console.log(e);
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: "Login gagal username/password salah",
      });
    },
  });
};
