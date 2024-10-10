const server_address = "http://localhost:8080";

async function saveWeight(event){
     event.preventDefault();
     let messageDiv = document.getElementById("message");
     messageDiv.innerText = ""
     data = await extractionDataFromForm();
     let response = await sendSave(data);
     await afterSend(response);
}

async function afterSend(response){
    if(response.ok) {
        console.log("Successful save weight");
        window.location.href = "/";
    }
    else{
        let responseObject = response.json()
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
            message = "Error during save of weight";
        }
        let messageDiv = document.getElementById("message");
        messageDiv.innerText = message;
        messageDiv.style.display = "block";
    }
}

async function sendSave(data){
    const url = server_address + '/body/weight/save';
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

async function extractionDataFromForm(){
    const form = document.getElementById("addWeightForm");
    const formData = new FormData(form);
    let data = {};
    data.weightInKg = formData.get("weightInKg");
    data.date = formData.get("date");
    return data;
}

let backButton = document.getElementById("backButton");
backButton.addEventListener("click", () => {
    window.location.href = "/";
});
(async () => {
    let today = new Date();
    document.getElementById('date').valueAsDate = today;
    document.addEventListener("DOMContentLoaded", () => {
        let form = document.getElementById("addWeightForm");
        form.addEventListener("submit", saveWeight);
    });
})();