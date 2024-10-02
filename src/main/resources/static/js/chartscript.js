async function getWeightBetweenDates(start, end){
      const url = "http://localhost:8080/weight/get/betweendates";
    try {
        const response = await fetch(url);
    if (!response.ok) {
      throw new Error(`Response status: ${response.status}`);
    }
    const weightList = await response.json();
        console.log("Data fetched successfully:", weightList);
    return weightList;
  } catch (error) {
    console.error(error.message);
    return null;
  }
}


async function getFirstWeightData(){
      const url = "http://localhost:8080/weight/get/first";
          try {
              const response = await fetch(url);
          if (!response.ok) {
            throw new Error(`Response status: ${response.status}`);
          }
          const weight = await response.json();
              console.log("Data fetched successfully:", weight);
          return weight;
        } catch (error) {
          console.error(error.message);
          return null;
        }
}

async function getLastWeightData(){
      const url = "http://localhost:8080/weight/get/last";
          try {
              const response = await fetch(url);
          if (!response.ok) {
            throw new Error(`Response status: ${response.status}`);
          }
          const weight = await response.json();
              console.log("Data fetched successfully:", weight);
          return weight;
        } catch (error) {
          console.error(error.message);
          return null;
        }

}

function calculateMinValueInChart(arrayWeight){
    var min = Math.min.apply( ...arrayWeight);
    var mod = min % 10;
    var chartMin;
    if(mod>=5){
        chartMin = Math.floor(min/10)*10-5;
    }
    else{
        chartMin = Math.floor(min/10)*10-10;
    }
    return chartMin;
}

function calculateMaxValueInChart(arrayWeight){
    var max = Math.max.apply( ...arrayWeight);
    var mod = max % 10;
    var chartMax;
    if(mod>=5){
        chartMax = Math.floor(max/10)*10+10;
    }
    else{
        chartMax = Math.floor(max/10)*10+5;
    }
    return chartMax;
}

(async ()=>{
    console.log("import succesful");
    var a = await getFirstWeightData();
    var startDate = document.getElementById('startDate').value;
    var endDate = document.getElementById("endDate").value;
    var weightList = await getWeightBetweenDates(startDate, endDate);
    var weightValueList = weightList.map(weight => {
    return weight.weight;
    }
    )


})();