console.log("hello word");

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
    let weightElement = document.createElement('span');
    weightElement.id = 'weight';
    weightElement.textContent = weight + " kg";
    let weightLabel = document.createElement('label');
    weightLabel.textContent = 'Weight: ';
    weightLabel.htmlFor = weightElement.id;
    summary.appendChild(weightLabel);
    summary.appendChild(weightElement);
}

function putBasicCircumferenceInsideDOM(basicCircumference){
    let summary = document.getElementById("body-summary");
    let basicCircumferenceContainer = document.createElement('div');
    for (let key in basicCircumference) {
        if (basicCircumference.hasOwnProperty(key)) {
            if(key!=='id'){
                let bodyPartElement = document.createElement('span');
                bodyPartElement.id = key;
                bodyPartElement.textContent = basicCircumference[key] + " cm ";
                let bodyPartLabel = document.createElement('label');
                bodyPartLabel.textContent = key + ": ";
                bodyPartLabel.htmlFor = key;
                basicCircumferenceContainer.appendChild(bodyPartLabel);
                basicCircumferenceContainer.appendChild(bodyPartElement);
            }
        }
    }
    summary.appendChild(basicCircumferenceContainer);
}


(async ()=>{
    var date = document.getElementById("date").innerText;
    var weight = await getWeightByDate(date);
    putWeightInsideDOM(weight);
    var measurementData = await getMeasurementData(date);
    if(measurementData.basicCircumference!==null){
        putBasicCircumferenceInsideDOM(measurementData.basicCircumference);
    }
    ///...
})();