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
        if(measurementData.basicCircumference!==null){
            basicCircumferenceContainer = putBasicCircumferenceInsideDOM(measurementData.basicCircumference, basicCircumferenceContainer);
            summary.appendChild(basicCircumferenceContainer);
        }
        else{
            let buttonAddBasicCircumference = createButton("Add basic circumference");
            basicCircumferenceContainer.appendChild(buttonAddBasicCircumference);
            summary.appendChild(basicCircumferenceContainer);
        }
        if(measurementData.additionalCircumference!==null){
            console.log(measurementData);
            additionalCircumferenceContainer = putAdditionalCircumferenceInsideDOM(measurementData.additionalCircumference, additionalCircumferenceContainer);
            summary.appendChild(additionalCircumferenceContainer);
        }
        else{
            let buttonAddAdditionalCircumference = createButton("Add additionalCircumference");
            additionalCircumferenceContainer.appendChild(buttonAddAdditionalCircumference);
            summary.appendChild(additionalCircumferenceContainer);
        }
    }
    else{
        let buttonAddBasicCircumference = createButton("Add basic circumference");
        let buttonAddAdditionalCircumference = createButton("Add additionalCircumference");
        basicCircumferenceContainer.appendChild(buttonAddBasicCircumference);
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
            }
        }
        basicCircumferenceContainer.appendChild(measurements);
        let buttonContainer = document.createElement("div");
        buttonContainer.id = "button-container-basic-data";
        let buttonEdit = createButton("Edit");
        buttonEdit.addEventListener("click", function() {
                                    edit("basicCircumferenceData");
                                    });
        buttonEdit.classList.add("button");
        buttonContainer.appendChild(buttonContainer);
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
            }
        }
        additionalCircumferenceContainer.appendChild(measurements);
        let buttonContainer = document.createElement("div");
        buttonContainer.id = "button-container-additional-data";
        let buttonEdit = createButton("Edit");
        buttonEdit.addEventListener("click", function() {
                                                 edit("additionalCircumferenceData");
                                             });
        buttonEdit.classList.add("button");
        buttonContainer.appendChild(buttonContainer);
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
    var editButton = buttonContainer.querySelector("button");
    var saveButton = createButton("start");
    saveButton.classList.add("button");
    var cancelButton = createButton("Cancel");
    cancelButton.classList.add("button");
    buttonContainer.replaceChild(cancelButton, editButton);
    buttonContainer.appendChild(saveButton);
}

function add(){
    console.log("add function run");
}

function save(){
    console.log("save function run");
}


function cancel(){
    console.log("cancel function run");
}

(async ()=>{
    var date = document.getElementById("date").innerText;
    var weight = await getWeightByDate(date);
    putWeightInsideDOM(weight);
    var measurementData = await getMeasurementData(date);
    putCircumferenceDataInsideDOM(measurementData);
    ///... must lock adding many record for one day eq measurementData
})();