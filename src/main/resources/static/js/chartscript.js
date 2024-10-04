let currentChart;

async function getWeightBefore(start){
    const url= 'http://localhost:8080/weight/get/before?date='+encodeURIComponent(start);
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

async function getWeightAfter(end){
    const url= 'http://localhost:8080/weight/get/after?date='+encodeURIComponent(end);
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

async function getWeightBetweenDates(start, end){
      const url = 'http://localhost:8080/weight/get/betweendates?start='+encodeURIComponent(start)+'&end='+encodeURIComponent(end);
      console.log("url " + url);
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
    var min = Math.min(...arrayWeight);
    var mod = min % 10;
    var chartMin;
    if(mod>=5){
        chartMin = Math.floor(min/10)*10-10;
    }
    else{
        chartMin = Math.floor(min/10)*10-5;
    }
    return chartMin;
}

function calculateMaxValueInChart(arrayWeight){
    var max = Math.max(...arrayWeight);
    var mod = max % 10;
    var chartMax;
    if(mod>=5){
        chartMax = Math.floor(max/10)*10+15;
    }
    else{
        chartMax = Math.floor(max/10)*10+10;
    }
    return chartMax;
}
function mapWeightOnDatesInChart(dates, weightList){
    var weightForDates = dates.map(date =>{
        const weightEntry = weightList.find(weight => date.getTime() === new Date(weight.date).getTime());
        return {
            date: date,
            weight: weightEntry ? weightEntry.weightInKg : null
        };
        });
    return weightForDates;
}

function generateRecordForDates(){
    const dates = []
    var startDate = document.getElementById('startDate').value;
    var endDate = new Date(document.getElementById("endDate").value);
    let currentDate = new Date(startDate);
    while(currentDate<=endDate){
        dates.push(new Date(currentDate));
        currentDate.setDate(currentDate.getDate() + 1);
    }
    return dates;
}

async function generateChart(data, minV, maxV, minDate, maxDate) {
    if (currentChart) {
        currentChart.destroy();
    }
  currentChart = new Chart(
    document.getElementById('weightChart'),
    {
      type: 'line',
      data: {
        labels: data.map(row => row.date.toISOString().split('T')[0]),
        datasets: [
          {
            label: 'Weight in kg',
            data: data.map(row => row.weight),
            spanGaps: true
          }
        ]
      },
      options: {
                  scales: {
                          x: {
                              type: 'time',
                              time: {
                                  unit: 'day'
                              },
                              min: new Date(minDate).getTime(),
                              max: new Date(maxDate).getTime()
                          },
                          y: {
                            min: minV,
                            max: maxV
                          }
                      }
      }
    }
  );
}


async function createChart(){
    var startDateFromInput = document.getElementById('startDate').value;
    var endDateFromInput = document.getElementById("endDate").value;
    var oneBefore = await getWeightBefore(startDateFromInput);
    var oneAfter = await getWeightAfter(endDateFromInput);
    var dateStart;
    var dateEnd;
    if(oneBefore.date!==null){
        console.log("+")
        console.log(oneBefore);
        dateStart = oneBefore.date;
    }
    else{
        dateStart = startDateFromInput;
    }
    if(oneAfter.date!==null){
        console.log("+")
        console.log(oneAfter);
        dateEnd = oneAfter.date;
    }
    else{
        dateEnd = endDateFromInput;
    }
    console.log("start" + dateStart);
    console.log("end" + dateEnd);
    var weightList = await getWeightBetweenDates(dateStart, dateEnd);
    var weights = weightList.map(weight => weight.weightInKg);
    var datesForChart = generateRecordForDates();
    var dataForChart = mapWeightOnDatesInChart(datesForChart, weightList);
    generateChart(dataForChart, calculateMinValueInChart(weights), calculateMaxValueInChart(weights));
}

(async function(){
    createChart();
})();
