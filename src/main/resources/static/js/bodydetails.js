const server_address = "http://localhost:8080";
//todo redirection after sending form
async function getBasicBodyData() {
    const url = server_address + "/body/basic/get/1";
    try {
        const response = await fetch(url);
    if (!response.ok) {
        throw new Error(`Response status: ${response.status}`);
    }
    const bodyData = await response.json();
    console.log("Data fetched successfully:", bodyData);
    return bodyData;
  }
  catch (error) {
    return null;
  }
}

function putInsideDom(bodyDetails){
    console.log(bodyDetails);
    let heightInput = document.getElementById("heightInCm")
    heightInCm.value = bodyDetails.heightInCm;
    let ageInput = document.getElementById("age");
    ageInput.value = bodyDetails.age;
    let genderInputId;
    if(bodyDetails.gender ==="m"){
        genderInputId = "male"
    }
    else if(bodyDetails.gender === "f"){
        genderInputId = "female"
    }
    else if(bodyDetails.gender === "o"){
        genderInputId = "other"
    }
    document.getElementById(genderInputId).checked = true;
}

(async ()=>{
    document.getElementById("bodyDetailsForm").onsubmit = function() {
        window.location.href = '/';
    };
    let bodyDetails = await getBasicBodyData();
    putInsideDom(bodyDetails);
})();