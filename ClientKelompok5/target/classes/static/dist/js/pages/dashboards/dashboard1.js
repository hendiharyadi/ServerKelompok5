/*
Template Name: Admin Pro Admin
Author: Wrappixel
Email: niravjoshi87@gmail.com
File: js
*/
/*$(function () {
  "use strict";
  // ==============================================================
  // Newsletter
  // ==============================================================

  var chart2 = new Chartist.Bar(
    ".amp-pxl",
    {
      labels: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
      series: [
        [9, 5, 3, 7, 5, 10, 3],
        [6, 3, 9, 5, 4, 6, 4],
      ],
    },
    {
      axisX: {
        // On the x-axis start means top and end means bottom
        position: "end",
        showGrid: false,
      },
      axisY: {
        // On the y-axis start means left and end means right
        position: "start",
      },
      high: "12",
      low: "0",
      plugins: [Chartist.plugins.tooltip()],
    }
  );

  var chart = [chart2];
});*/
const canvas = document.querySelector("canvas");
const ctx = canvas.getContext("2d");
const filterDate = (date) => {
  return new Date(date).toLocaleString("id", { month: "long" });
};
function displayChart(count) {
  const data = {
    labels: ["Manager", "User"],
    datasets: [
      {
        label: "Data user berdasakan role",
        fill: false,
        lineTension: 0.1,
        backgroundColor: "#1a9bfc",
        borderColor: "#302b63",
        borderCapStyle: "butt",
        borderDash: [],
        borderDashOffset: 0.0,
        borderJoinStyle: "miter",
        pointBorderColor: "rgba(75,192,192,1)",
        pointBackgroundColor: "#fff",
        pointBorderWidth: 1,
        pointHoverRadius: 5,
        pointHoverBackgroundColor: "rgba(75,192,192,1)",
        pointHoverBorderColor: "rgba(220,220,220,1)",
        pointHoverBorderWidth: 2,
        pointRadius: 1,
        pointHitRadius: 10,
        data: count,
        spanGaps: false,
        // barPercentage: 0.5,
        barThickness: 40,
        maxBarThickness: 40,
        minBarLength: 6,
      },
    ],
  };
  const options = {
    scales: {
      yAxes: [
        {
          ticks: {
            beginAtZero: true,
          },
        },
      ],
    },
  };
  return new Chart(ctx, {
    type: "bar",
    data: data,
    options: options,
  });
}
const getData = async () => {
  try {
    const finishConvertedDate = [];
    const res = await fetch("/api/employee");
    const data = await res.json();

    const arrUser = [];
    const arrManager = [];
    const user = data.map((e) => {
      if (e.user.userRole[0].name === "ROLE_USER") {
        arrUser.push(e);
      } else if (e.user.userRole[0].name === "ROLE_MANAGER") {
        arrManager.push(e);
      }
    });
    displayChart([arrManager.length, arrUser.length]);
  } catch (error) {
    console.log(error);
  }
};
window.addEventListener("DOMContentLoaded", () => {
  getData();
});
