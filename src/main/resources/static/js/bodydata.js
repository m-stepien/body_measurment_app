//todo adding error message
//todo refaktor this complete mess
//todo cleaning procedures in database
const server_address = "http://localhost:8080";
let ids = {};
let basicCircumferenceKeyArray = ["abdominal", "chest", "hip", "waist"];
let additionalCircumferenceKeyArray = ["armL", "armR", "calfL", "calfR", "forarmL", "forarmR", "neck", "thighL", "thighR"];
var measurementData;

async function getWeightByDate(date){
        const url = server_address + '/body/weight/get/betweendates?start=' + encodeURIComponent(date)
                                                             + '&end=' + encodeURIComponent(date);
        let weightList = await getData(url);
        if(weightList.length){
            let weight = weightList[0];
            ids.weightId = weight.id
            return weight.weightInKg;
        }
        else{
            return null;
        }
}

async function getMeasurementData(date){
    const url = server_address + '/circumference/betweenDates?start='
                       + encodeURIComponent(date) + '&end=' + encodeURIComponent(date);
    let measurementData = await getData(url);
    if(measurementData.length){
        return measurementData[0];
    }
    else{
        return null;
    }
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
        console.error(error.message);
        return null;
    }
}

function putWeightInsideDOM(weight){
    let summary = document.getElementById("body-summary");
    let weightContainer = document.createElement("div");
    weightContainer.classList.add('weight-section')
    let weightElement = document.createElement('span');
    weightElement.id = 'weight';
    weightElement.textContent = weight + " kg";
    let weightLabel = document.createElement('label');
    weightLabel.textContent = 'Weight: ';
    weightLabel.htmlFor = weightElement.id;
    weightUpdateButton = createButton("Edit");
    weightUpdateButton.addEventListener("click", function() {
                        updateWeight();
                    });
    weightUpdateButton.classList.add("button");
    weightUpdateButton.id = "updateWeightButton";
    weightContainer.appendChild(weightLabel);
    weightContainer.appendChild(weightElement);
    weightContainer.appendChild(weightUpdateButton);
    summary.appendChild(weightContainer);
}

function putCircumferenceDataInsideDOM(measurementData){
    let summary = document.getElementById("body-summary");
    let basicCircumferenceContainer = document.createElement('div');
    let additionalCircumferenceContainer = document.createElement('div');
    if(measurementData!==null){
        ids["main_id"]=measurementData["id"];
        if(measurementData.basicCircumference!==null){
            basicCircumferenceContainer = putBasicCircumferenceInsideDOM(measurementData.basicCircumference, basicCircumferenceContainer);
            summary.appendChild(basicCircumferenceContainer);
        }
        else{
            basicCircumferenceContainer.classList.add("measurement-section");
            let header = document.createElement("h2");
            header.textContent = "Body Measurements";
            let buttonAddBasicCircumference = createButton("Add");
            buttonAddBasicCircumference.addEventListener("click", function() {
                add(basicCircumferenceContainer);
            });
            buttonAddBasicCircumference.classList.add("button");
            basicCircumferenceContainer.appendChild(header);
            basicCircumferenceContainer.appendChild(buttonAddBasicCircumference);
            summary.appendChild(basicCircumferenceContainer);
        }
        if(measurementData.additionalCircumference!==null){
            additionalCircumferenceContainer = putAdditionalCircumferenceInsideDOM(measurementData.additionalCircumference, additionalCircumferenceContainer);
            summary.appendChild(additionalCircumferenceContainer);
        }
        else{
            additionalCircumferenceContainer.classList.add("measurement-section");
            let header = document.createElement("h2");
            header.textContent = "Limbs Measurements";
            let buttonAddAdditionalCircumference = createButton("Add");
            buttonAddAdditionalCircumference.addEventListener("click", function() {
                                                add(additionalCircumferenceContainer);
                                                });
            buttonAddAdditionalCircumference.classList.add("button");
            additionalCircumferenceContainer.appendChild(header);
            additionalCircumferenceContainer.appendChild(buttonAddAdditionalCircumference);
            summary.appendChild(additionalCircumferenceContainer);
        }
    }
    else{
        basicCircumferenceContainer.classList.add("measurement-section");
        let header = document.createElement("h2");
        header.textContent = "Body Measurements";
        additionalCircumferenceContainer.classList.add("measurement-section");
        let header2 = document.createElement("h2");
        header2.textContent = "Limbs Measurements";
        let buttonAddBasicCircumference = createButton("Add");
        let buttonAddAdditionalCircumference = createButton("Add");
        buttonAddBasicCircumference.addEventListener("click", function() {
            add(basicCircumferenceContainer);
        });
        buttonAddAdditionalCircumference.addEventListener("click", function() {
            add(additionalCircumferenceContainer);
            });
        buttonAddBasicCircumference.classList.add("button");
        buttonAddAdditionalCircumference.classList.add("button");
        basicCircumferenceContainer.appendChild(header);
        basicCircumferenceContainer.appendChild(buttonAddBasicCircumference);
        additionalCircumferenceContainer.appendChild(header2);
        additionalCircumferenceContainer.appendChild(buttonAddAdditionalCircumference);
        summary.appendChild(basicCircumferenceContainer);
        summary.appendChild(additionalCircumferenceContainer);
    }
}

function putDeleteRecordInsideDOM(){
    var summary = document.getElementById("body-summary");
    var deleteButton = createButton("Delete");
    deleteButton.classList.add("delete-button");
    deleteButton.id = "delete-button";
    deleteButton.addEventListener("click", function() {
                           deleteRecord();
                           });
    summary.appendChild(deleteButton);
}

function createButton(text){
        let button = document.createElement('button');
        button.textContent = text;
        return button;
}


function putBasicCircumferenceInsideDOM(basicCircumference, basicCircumferenceContainer){
        basicCircumferenceContainer.classList.add("measurement-section");
        let header = document.createElement("h2");
        header.textContent = "Body Measurements";
        basicCircumferenceContainer.appendChild(header);
        let measurements = document.createElement("measurements");
        measurements.id = "basicCircumferenceData";
        measurements.classList.add("body-measurements");
        for (let key in basicCircumference) {
            if (basicCircumference.hasOwnProperty(key)) {
                if(key !== 'id'){
                    let measurement = document.createElement("div");
                    measurement.classList.add("measurement");
                    let bodyPartElement = document.createElement('span');
                    bodyPartElement.id = key;
                    bodyPartElement.textContent = basicCircumference[key] !== null ? basicCircumference[key] + " cm " : "";
                    let bodyPartLabel = document.createElement('label');
                    bodyPartLabel.textContent = createLabelFromKey(key) + ": ";
                    bodyPartLabel.htmlFor = key;
                    measurement.appendChild(bodyPartLabel);
                    measurement.appendChild(bodyPartElement);
                    measurements.appendChild(measurement);
                }
                else{
                    ids["basic_id"] = basicCircumference[key];
                }
            }
        }
        basicCircumferenceContainer.appendChild(measurements);
        let buttonContainer = document.createElement("div");
        buttonContainer.id = "button-container-basic-data";
        let buttonEdit = createButton("Edit");
        buttonEdit.addEventListener("click", function() {
                                    edit("basicCircumferenceData", "button-container-basic-data");
                                    });
        buttonEdit.classList.add("button");
        buttonContainer.appendChild(buttonEdit);
        basicCircumferenceContainer.appendChild(buttonContainer);
        return basicCircumferenceContainer;
}


function putAdditionalCircumferenceInsideDOM(additionalCircumference, additionalCircumferenceContainer){
        additionalCircumferenceContainer.classList.add("measurement-section");
        let header = document.createElement("h2");
        header.textContent = "Limbs Measurements";
        additionalCircumferenceContainer.appendChild(header);
        let measurements = document.createElement("div");
        measurements.id = "additionalCircumferenceData";
        measurements.classList.add("measurements");
        measurements.classList.add("limbs-measurements");
        for (let key in additionalCircumference) {
            if (additionalCircumference.hasOwnProperty(key)) {
                if(key !== 'id'){
                    let measurement = document.createElement("div");
                    measurement.classList.add("measurement");
                    let bodyPartElement = document.createElement('span');
                    bodyPartElement.id = key;
                    bodyPartElement.textContent = additionalCircumference[key] !== null ? additionalCircumference[key] + " cm " : "";
                    let bodyPartLabel = document.createElement('label');
                    bodyPartLabel.textContent = createLabelFromKey(key) + ": ";
                    bodyPartLabel.htmlFor = key;
                    measurement.appendChild(bodyPartLabel);
                    measurement.appendChild(bodyPartElement);
                    measurements.appendChild(measurement);
                }
                else{
                    ids["additional_id"] = additionalCircumference[key];
                }
            }
        }
        additionalCircumferenceContainer.appendChild(measurements);
        let buttonContainer = document.createElement("div");
        buttonContainer.id = "button-container-additional-data";
        let buttonEdit = createButton("Edit");
        buttonEdit.addEventListener("click", function() {
                                                 edit("additionalCircumferenceData", "button-container-additional-data");
                                             });
        buttonEdit.classList.add("button");
        buttonContainer.appendChild(buttonEdit);
        additionalCircumferenceContainer.appendChild(buttonContainer);
        return additionalCircumferenceContainer;
}

function createLabelFromKey(key){
    const match = key.match(/[A-Z]/);
    let label;
    let idx = match ? key.indexOf(match[0]) : -1;
    if(idx>=0){
        let limb = key.slice(0, idx);
        let side;
        if(key.slice(idx)==="R"){
            side = "Right";
        }
        else{
            side = "Left";
        }
        label = limb + " " + side;
    }
    else{
        label = key;
    }
    label = label.charAt(0).toUpperCase() + label.slice(1);
    return label;
}

async function initReadView(){
    var date = document.getElementById("date").innerText;
    var weight = await getWeightByDate(date);
    console.log(weight);
    putWeightInsideDOM(weight);
    measurementData = await getMeasurementData(date);
    putCircumferenceDataInsideDOM(measurementData);
    putDeleteRecordInsideDOM();
}

function edit(measurementsDataId, buttonContainerId){
    var container = document.getElementById(measurementsDataId);
    for(var i = 0; i<container.children.length; i++){
        var child = container.children[i];
        var spanElement = child.querySelector('span');
        var defaultValue = spanElement.innerHTML;
        var id = spanElement.id;
        var inputElem = document.createElement("input");
        inputElem.type = "number";
        inputElem.id = id;
        inputElem.value = parseFloat(defaultValue);
        inputElem.step = '0.01';
        inputElem.classList.add("styled-input")
        child.replaceChild(inputElem, spanElement);
    }
    var buttonContainer = document.getElementById(buttonContainerId);
    var editButton = buttonContainer.querySelector("button");
    var saveButton = createButton("Save");
    saveButton.classList.add("button");
    var cancelButton = createButton("Cancel");
    cancelButton.classList.add("button");
    cancelButton.addEventListener("click", cancel);
    saveButton.addEventListener("click", function() {
                                                        save(measurementsDataId);
                                                    });
    buttonContainer.replaceChild(cancelButton, editButton);
    buttonContainer.appendChild(saveButton);
}

async function addNew(dataSectionId){
    var dataSection = document.getElementById(dataSectionId);
    var changed = {};
    dataSection.getElementsByClassName("measurements");
    for(var i = 0; i < dataSection.children.length; i++){
        var inputElem = dataSection.children[i].querySelector('input');
        changed[inputElem.id] = inputElem.value
    }
    var preparedToSend = prepareDataToSendNew(changed, dataSectionId);
    let response = await sendNew(preparedToSend);
    let responseObject = await response.json();
    if(response.ok){
        cancel();
    }
    else{
        let err = responseObject.error;
        console.error(err);
        console.error(responseObject.details);
        showErrorAlert(err);
    }
}


function add(section){
    console.log("add function run");
    section.children[section.children.length-1].remove();//delete add button
    var measurements = document.createElement("div");
    measurements.classList.add("measurements");
    var sectionsElement = document.getElementsByClassName("measurement-section");
    let keyArray;
    if(section===sectionsElement[0]){
        measurements.classList.add("body-measurements");
        measurements.id = "basicCircumferenceData";
        keyArray = basicCircumferenceKeyArray;

    }
    else{
        measurements.classList.add("limbs-measurements");
        measurements.id = "additionalCircumferenceData";
        keyArray = additionalCircumferenceKeyArray;
    }
    for(var i=0;i<keyArray.length;i++){
        var measurement = document.createElement("div");
        measurement.classList.add("measurement");
        var label = document.createElement("label");
        label.textContent = createLabelFromKey(keyArray[i]);
        label.htmlFor = keyArray[i];
        var input = document.createElement("input");
        input.id = keyArray[i];
        input.type = "number";
        input.step = '0.01';
        input.classList.add("styled-input")
        measurement.appendChild(label);
        measurement.appendChild(input);
        measurements.appendChild(measurement);
    }
    section.appendChild(measurements);
    var buttonContainer = document.createElement("div");
    var editButton = buttonContainer.querySelector("button");
    var saveButton = createButton("Save");
    saveButton.classList.add("button");
    var cancelButton = createButton("Cancel");
    cancelButton.classList.add("button");
    cancelButton.addEventListener("click", cancel);
    if(measurementData == null){
    saveButton.addEventListener("click", function() {
                                                     addNew(measurements.id);
                                                    });
    }
    else{
        saveButton.addEventListener("click", function() {
                                                         save(measurements.id);
                                                        });
    }
    buttonContainer.appendChild(cancelButton, editButton);
    buttonContainer.appendChild(saveButton);
    section.appendChild(buttonContainer);

}

async function save(dataSectionId){
    var dataSection = document.getElementById(dataSectionId);
    var changed = {};
    dataSection.getElementsByClassName("measurements");
    for(var i = 0; i < dataSection.children.length; i++){
        var inputElem = dataSection.children[i].querySelector('input');
        changed[inputElem.id] = inputElem.value
    }
    var preparedToSend = prepareDataToSendUpdate(changed, dataSectionId);
    let response = await sendUpdate(preparedToSend);
    let responseObject = await response.json();
    if(response.ok){
        cancel();
    }
    else{
        let err = responseObject.error
        console.error(err);
        console.error(responseObject.details);
        showErrorAlert(err);
    }
}

function prepareDataToSendUpdate(changed, sectionId){
    var toSend = {};
    toSend["id"] = ids["main_id"];
    toSend["measurementDate"] = document.getElementById("date").innerText;
    if(sectionId==="additionalCircumferenceData"){
        toSend["additionalCircumference"] = changed;
        toSend["basicCircumference"] = null;
    }
    else{
        toSend["additionalCircumference"] = null;
        toSend["basicCircumference"] = changed;
    }
    return toSend;
}

function prepareDataToSendNew(changed, sectionId){
    var toSend = {};
    toSend["measurementDate"] = document.getElementById("date").innerText;
    if(sectionId==="additionalCircumferenceData"){
        toSend["additionalCircumference"] = changed;
        toSend["basicCircumference"] = null;
    }
    else{
        toSend["additionalCircumference"] = null;
        toSend["basicCircumference"] = changed;
    }
    return toSend;
}

async function sendNew(toSend){
    const url = server_address + '/circumference/addNewCircumference';
    let response = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(toSend)
    })
    .catch(error => console.error('Error:', error));
    return response;
}

async function sendUpdate(toSend){
    const url = server_address + '/circumference/update/'+encodeURIComponent(toSend.id);
    let response = await fetch(url, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(toSend)
    })
    .catch(error => console.error('Error:', error));
    return response;
}


function cancel(){
    var sectionsElement = document.getElementById("body-summary");
    for(var i = sectionsElement.children.length-1; i > 0; i--){//skip date
        sectionsElement.children[i].remove();
    }
    initReadView();
}

async function saveUpdateWeight(){
    let toSend = await prepareWeightToSend();
    let response = await sendUpdateWeight(toSend);
    let responseObject = await response.json();
    if(response.ok){
            cancel();
    }
    else{
        let err = responseObject.error;
        console.error(err);
        console.error(responseObject.details);
        showErrorAlert(err);
    }

}
async function prepareWeightToSend(){
        let inputWeight = document.getElementById("weight");
        let updatedWeight = inputWeight.value;
        let toSend = {};
        toSend.id = ids.weightId;
        toSend.weightInKg = updatedWeight;
        return toSend;
}
async function sendUpdateWeight(toSend){
    const url = server_address + "/body/weight/update"
    let response = await fetch(url, {
        method: 'PATCH',
        headers: {
        'Content-Type': 'application/json'
        },
        body: JSON.stringify(toSend)
        })
        .catch(error => console.error('Error:', error));
    return response;
}

async function updateWeight(){
    let weightSpan = document.getElementById("weight");
    let defaultValue = weightSpan.innerText;
    let weightUpdateButton = document.getElementById("updateWeightButton");
    let weightInput = document.createElement("input");
    weightInput.type = "number";
    weightInput.step = '0.01';
    weightInput.classList.add("styled-input");
    weightInput.id = "weight";
    weightInput.value = parseFloat(defaultValue);
    const parent = weightSpan.parentNode;
    parent.replaceChild(weightInput, weightSpan);
    parent.removeChild(weightUpdateButton);
    let cancelButton = createButton("Cancel");
    cancelButton.addEventListener("click", function() {
                    cancel();
                });
    cancelButton.classList.add("button");
    let saveButton = createButton("Save");
    saveButton.classList.add("button");
    saveButton.addEventListener("click", function() {
                        saveUpdateWeight()
    });
    parent.appendChild(cancelButton);
    parent.appendChild(saveButton);
}

async function deleteMeasurementData(id){
        const url = server_address + "/circumference/delete/" + encodeURIComponent(id)
        let response = await fetch(url, {
            method: 'DELETE',
            headers: {
            'Content-Type': 'application/json'
            }
            })
            .catch(error => console.error('Error:', error));
        return response;
}

async function deleteWeight(id){
    const url = server_address + "/body/weight/delete/" + encodeURIComponent(id)
    let response = await fetch(url, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
        })
        .catch(error => console.error('Error:', error));
    return response;
}

async function deleteRecord(){
    let measurementsId = ids.main_id;
    let weightId = ids.weightId;
    let operationOnMeasurementOk = true;
    if(measurementsId){
        let responseMeasurement = await deleteMeasurementData(measurementsId);
        if(!responseMeasurement.ok){
            operationOnMeasurementOk = false;
            let responseObject = responseMeasurement.json();
            console.error(responseObject.error);
            console.error(responseObject.details);
        }
    }
    let responseWeight = await deleteWeight(weightId);
    console.log(responseWeight);
    if(responseWeight.ok){
        window.location.href = "/";
    }
    else{
        let responseObject = responseWeight.json();
        console.error(responseObject.error);
        console.error(responseObject.details);
        showErrorAlert(responseObject.error);
    }
}

function showErrorAlert(errorMessage) {
                let overlay = document.createElement("div");
                overlay.classList.add("overlay");
                let errorAlert = document.createElement("div");
                errorAlert.classList.add("error-alert");
                errorAlert.textContent = "Error: " + errorMessage;
                const closeButton = document.createElement("button");
                closeButton.textContent = "Close";
                closeButton.classList.add("close-button");
                closeButton.addEventListener("click", () => {
                    overlay.remove();
                });
                errorAlert.appendChild(closeButton);
                overlay.appendChild(errorAlert);
                document.body.appendChild(overlay);
}

let backButton = document.getElementById("backButton");
backButton.addEventListener("click", () => {
    window.location.href = "/";
});
(async () => {
    initReadView();
})();