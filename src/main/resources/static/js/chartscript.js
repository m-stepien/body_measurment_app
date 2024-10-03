async function getWeightBetweenDates(start, end){
      const url = 'http://localhost:8080/weight/get/betweendates?start='+encodeURIComponent(start)+'&end='+encodeURIComponent(end);
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
function mapWeightOnDatesInChart(dates, weightList){
    var weightForDates = dates.map(date =>{
        const weightEntry = weightList.find(weight => date.getTime() === weight.date.getTime());
        return {
            date: date,
            weight: weightEntry ? weightEntry.weightKg : null
        };
        });
    return weightForDates;
}

function generateRecordForDates(){
    const dates = []
    var startDate = document.getElementById('startDate').value;
    var endDate = document.getElementById("endDate").value;
    let currentDate = startDate;
    while(currentDate<=endDate){
        dates.push(new Date(currentDate));
        currentDate.setDate(currentDate.getDate() + 1);
    }
    return dates;
}

async function createChart() {
  const data = [
    { date: new Date('2010-01-01'), weight: 10 },
    { date: new Date('2010-01-02'), weight: 12 },
    { date: new Date('2010-01-03'), weight: 15 },
    { date: new Date('2010-01-04'), weight: null },
    { date: new Date('2010-01-05'), weight: 25 },
    { date: new Date('2010-01-06'), weight: 30 },
  ];
  new Chart(
    document.getElementById('weightChart'),
    {
      type: 'line',
      data: {
        labels: data.map(row => row.date.toISOString().split('T')[0]),
        datasets: [
          {
            label: 'Acquisitions by year',
            data: data.map(row => row.weight),
            spanGaps: true
          }
        ]
      },
      options: {
              y: {
                  min: 5,
                  max: 35
                }
      }
    }
  );
}

async function createChart2(data, minV, maxV) {
  new Chart(
    document.getElementById('weightChart'),
    {
      type: 'line',
      data: {
        labels: data.map(row => row.date.toISOString().split('T')[0]),
        datasets: [
          {
            label: 'Weight in kg',
            data: data.map(row => row.weightKg),
            spanGaps: true
          }
        ]
      },
      options: {
              y: {
                  min: minV,
                  max: maxV
                }
      }
    }
  );
}


(async function(){
    var a = await getFirstWeightData();
    var startDate = document.getElementById('startDate').value;
    var endDate = document.getElementById("endDate").value;
    var weightList = await getWeightBetweenDates(startDate, endDate);
    console.log(weightList);
    var datesForChart = generateRecordForDates();
    var dataForChart = mapWeightOnDatesInChart(datesForChart, weightList);
    console.log(datesForChart);
    console.log(dataForChart);
    createChart();
    console.log(generateRecordForDates());
})();
