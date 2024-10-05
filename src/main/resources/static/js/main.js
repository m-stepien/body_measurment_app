async function getBasicBodyData() {
  const url = "http://localhost:8080/body/basic/get/1";
  try {
    const response = await fetch(url);
    if (!response.ok) {
      throw new Error(`Response status: ${response.status}`);
    }
    const bodyData = await response.json();
    console.log("Data fetched successfully:", bodyData);
    return bodyData;
  } catch (error) {
    return null;
  }
}

async function getOldestWeightRecord(){

 const url = "http://localhost:8080/weight/get/first";
  try {
    const response = await fetch(url);
    if (!response.ok) {
      throw new Error(`Response status: ${response.status}`);
    }
    const bodyData = await response.json();
        console.log("Data fetched successfully:", bodyData);
    return bodyData;
  } catch (error) {
    return null;
  }
}

function putDataToSummary(bodyData, lastWeight) {
  if (!bodyData) {
    console.error("No data to populate in the DOM.");
    return;
  }

  var age = document.getElementById("age");
  age.innerText = bodyData["age"];

  var height = document.getElementById("height");
  height.innerText  = bodyData["heightInCm"];

  var gender = document.getElementById("gender");
  if (bodyData["gender"] === "m") {
    gender.innerText  = "Male";
  } else {
    gender.innerText  = "Female";
  }
  var currentWeight = document.getElementById("currentWeight");
  if(lastWeight!==null){
    currentWeight.innerText = lastWeight.weightInKg;
  }

}
let today = new Date();
let before = new Date(today);
before.setMonth(today.getMonth() - 1);
document.getElementById('startDate').valueAsDate = before;
document.getElementById('endDate').valueAsDate = today;
(async function() {
  var bd = await getBasicBodyData();
  var lastWeight = await getOldestWeightRecord();
  putDataToSummary(bd, lastWeight);
})();
