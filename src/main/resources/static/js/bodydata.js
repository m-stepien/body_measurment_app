let ids = {};
let basicCircumferenceKeyArray = ["abdominal", "chest", "hip", "waist"];
let additionalCircumferenceKeyArray = ["armL", "armR", "calfL", "calfR", "forarmL", "forarmR", "neck", "thighL", "thighR"];
var measurementData;

async function getWeightByDate(date){
        const url = 'http://localhost:8080/weight/get/betweendates?start='+encodeURIComponent(date)+'&end='+encodeURIComponent(date);
            try {
                const response = await fetch(url);
            if (!response.ok) {
              throw new Error(`Response status: ${response.status}`);
            }
            const weightList = await response.json();
            console.log("Data fetched successfully:", weightList);
            return weightList[0].weightInKg;
          } catch (error) {
                console.error(error.message);
            return null;
          }
}

async function getMeasurementData(date){
    const url = 'http://localhost:8080/bodyMonitoring/getCircumference/betweenDate?start='+encodeURIComponent(date)+'&end='+encodeURIComponent(date);
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`Response status: ${response.status}`);
        }
        const measurementData = await response.json();
        console.log("Data fetched successfully:", measurementData);
        if(measurementData.length){
            return measurementData[0];
        }
        else{
            return null;
        }
        } catch (error) {
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
    weightContainer.appendChild(weightLabel);
    weightContainer.appendChild(weightElement);
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
            console.log(measurementData);
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
        header.textContent = "Limbs Measurements";
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
    putWeightInsideDOM(weight);
    measurementData = await getMeasurementData(date);
    putCircumferenceDataInsideDOM(measurementData);
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
        console.log(defaultValue);
        inputElem.value = parseFloat(defaultValue);
        inputElem.step = '0.01';
        inputElem.classList.add("styled-input")
        child.replaceChild(inputElem, spanElement);
    }
    var buttonContainer = document.getElementById(buttonContainerId);
    console.log("buttonContainerId" + buttonContainerId);
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
    await sendNew(preparedToSend);
    cancel();
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
    await sendUpdate(preparedToSend);
    cancel();
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
    const url = 'http://localhost:8080/bodyMonitoring/addNewCircumference';
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(toSend)
    })
    .catch(error => console.error('Error:', error));
}

async function sendUpdate(toSend){
    const url = 'http://localhost:8080/bodyMonitoring/update/circumference/'+encodeURIComponent(toSend.id);
    fetch(url, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(toSend)
    })
    .catch(error => console.error('Error:', error));
}


function cancel(){
    var sectionsElement = document.getElementById("body-summary");
    console.log(sectionsElement.children.length);
    for(var i = sectionsElement.children.length-1; i > 0; i--){//skip date
        sectionsElement.children[i].remove();
    }
    initReadView();
}

(async ()=>{
    initReadView();
    ///... must lock adding many record for one day eq measurementData
})();