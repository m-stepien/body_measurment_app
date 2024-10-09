//todo back button in subsite
const server_address = "http://localhost:8080";
const numberOfMonthsBackDefault = 1;


async function getBasicBodyData() {
    const url = server_address + "/body/basic/get/1";
    return getData(url);
}

async function getOldestWeightRecord(){
    const url = server_address + "/weight/get/first";
    return getData(url);
}

async function getData(url){
    try {
        const response = await fetch(url);
        if (!response.ok) {
        throw new Error(`Response status: ${response.status}`);
        }
        const dataObject = await response.json();
        console.log("Data fetched successfully:", dataObject);
        return dataObject;
    }
    catch (error) {
        console.error(error);
        return null;
    }
}

function putDataToSummary(bodyData, lastWeight) {
    if (bodyData) {
        var age = document.getElementById("age");
        age.innerText = bodyData["age"];

        var height = document.getElementById("height");
        height.innerText  = bodyData["heightInCm"];

        var gender = document.getElementById("gender");
        if (bodyData["gender"] === "m") {
            gender.innerText  = "Male";
        }
        else {
            gender.innerText  = "Female";
        }
        var currentWeight = document.getElementById("currentWeight");
        if(lastWeight!==null){
            currentWeight.innerText = lastWeight.weightInKg;
        }
    }
}


let today = new Date();
let before = new Date(today);
before.setMonth(today.getMonth() - numberOfMonthsBackDefault);
document.getElementById('startDate').valueAsDate = before;
document.getElementById('endDate').valueAsDate = today;
(async function() {
  var bd = await getBasicBodyData();
  var lastWeight = await getOldestWeightRecord();
  putDataToSummary(bd, lastWeight);
})();
