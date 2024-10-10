const server_address = "http://localhost:8080";

async function getBasicBodyData() {
    const url = server_address + "/body/details/get/1";
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
        console.error(error);
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

async function saveBodyDetails(event){
     event.preventDefault();
     let messageDiv = document.getElementById("message");
     messageDiv.innerText = "";
     data = await extractionDataFromForm();
     let response = await sendSave(data);
     await afterSend(response);
}

async function extractionDataFromForm(){
    const form = document.getElementById("bodyDetailsForm");
    const formData = new FormData(form);
    let data = {};
    //until adding users
    data.id = 1;
    data.heightInCm = formData.get("heightInCm");
    data.age = formData.get("age");
    data.gender = formData.get("gender");
    return data;
}

async function sendSave(data){
    const url = server_address + '/body/details/save';
    try {
         const response = await fetch(url, {
         method: "POST",
         headers: {
            'Content-Type':'application/json'
         },
         body: JSON.stringify(data)
         });
         return response;
    } catch(error){
        console.error("Error: ", error);
    }
}

async function afterSend(response){
    if(response.ok) {
        console.log("Successful save weight");
        window.location.href = "/";
    }
    else{
        let responseObject = await response.json()
        let err = responseObject.error;
        console.error(err);
        console.error(responseObject.details);
        let message;
        if(err === "Missing data"){
            message = "Missing required data";
        }
        else if(err === "Invalid data"){
            message = err;
        }
        else{
            message = "Error during save of details";
        }
        let messageDiv = document.getElementById("message");
        messageDiv.innerText = message;
        messageDiv.style.display = "block";
    }
}


document.addEventListener("DOMContentLoaded", () => {
            let form = document.getElementById("bodyDetailsForm");
            form.addEventListener("submit", saveBodyDetails);
});

(async ()=>{
    let bodyDetails = await getBasicBodyData();
    putInsideDom(bodyDetails);
})();