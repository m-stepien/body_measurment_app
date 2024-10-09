let currentChart;
let cachedLabel = {};
const ctx = document.getElementById('weightChart').getContext('2d');


async function getWeightBefore(start){
    const url= server_address + '/body/weight/get/before?date='+encodeURIComponent(start);
    let weightBefore = await getData(url);
    if(weightBefore==null || weightBefore.date==null){
        return null;
    }
    else{
        return weightBefore;
    }
    }

async function getWeightAfter(end) {
    const url= server_address + '/body/weight/get/after?date=' + encodeURIComponent(end);
    let weightAfter = await getData(url);
    if(weightAfter == null || weightAfter.date == null){
        return null;
    }
    else{
        return weightAfter;
    }
    }

async function getWeightBetweenDates(start, end) {
    const url = server_address + '/body/weight/get/betweendates?start=' + encodeURIComponent(start)
                                                             + '&end=' + encodeURIComponent(end);
    return getData(url);
    }

async function getFirstWeightData(){
    const url = server_address + "/body/weight/get/first";
    return getData(url);
    }

async function getLastWeightData() {
    const url = server_address + "/body/weight/get/last";
    return getData(url);
    }

async function getBasicCircumference(date) {
    const url = server_address + '/circumference/basic?date=' + encodeURIComponent(date);
    let basicCircumference = await getData(url);
    if(basicCircumference === null || basicCircumference.id === null) {
        return null;
        }
    else {
        return basicCircumference;
        }
    }

async function getData(url) {
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`Response status: ${response.status}`);
            }
        const dataObject = await response.json();
        console.log("Data fetched successfully:", dataObject);
        return dataObject
        }
    catch (error) {
        console.error(error.message);
        return null;
        }
    }

function calculateMinValueInChart(arrayWeight) {
    var min = calculateLimitValueInChart(arrayWeight, -10, -5)
    return min;
    }

function calculateMaxValueInChart(arrayWeight) {
    var max = calculateLimitValueInChart(arrayWeight, 15, 10)
    return max;
    }

function calculateLimitValueInChart(arrayWeight, c, d) {
    var limit;
    if(c>0){
        limit = Math.max(...arrayWeight);
    }
    else{
        limit = Math.min(...arrayWeight);
    }
    var mod = limit % 10;
    var chartLimit;
    if(mod >= 5) {
        chartLimit = Math.floor(limit/10)*10+c;
        }
    else {
        chartLimit = Math.floor(limit/10)*10+d;
        }
    return chartLimit;
    }

function mapWeightOnDatesInChart(dates, weightList) {
    var weightForDates = dates.map(date =>{
        const weightEntry = weightList.find(weight => date.getTime() === new Date(weight.date).getTime());
        return {
            date: date,
            weight: weightEntry ? weightEntry.weightInKg : null
            };
        });
    return weightForDates;
    }


function generateRecordForDates(startDateStr, endDateStr) {
    const dates = []
    let currentDate = new Date(startDateStr);
    let endDate = new Date(endDateStr);
    while(currentDate <= endDate) {
        dates.push(new Date(currentDate));
        currentDate.setDate(currentDate.getDate() + 1);
        }
    return dates;
    }

async function createArrayToShow(weightStr, basicCircumference) {
    var arrayToShow = [];
    arrayToShow.push(weightStr);
    for (let key in basicCircumference) {
        if (basicCircumference.hasOwnProperty(key)) {
            if(key!=='id'){
                arrayToShow.push(`${key}: ${basicCircumference[key]} cm`);
                }
            }
        }
    arrayToShow.push(``);
    arrayToShow.push("Click to see more detail or edit");
    return arrayToShow;
    }

async function generateChart(data, minV, maxV, minDate, maxDate) {
    if (currentChart) {
        currentChart.destroy();
        }
    let chartData = data.map(row => {
        return {
            x: row.date.toISOString().split('T')[0],
            y: row.weight,
            info: "Click to see more detail or edit"
        };});
    currentChart = new Chart(
        ctx,
        {
          type: 'line',
          data: {
            datasets:[
              {
                label: 'Weight in kg',
                data: chartData,
                spanGaps: true,
                fill: false,
                stepped: false,
              }
            ]
          },
          options: {
                      scales: {
                              x: {
                                  type: 'time',
                                  time: {
                                      unit: 'day',
                                      tooltipFormat: 'dd/MM/yyyy'
                                  },
                                  min: new Date(minDate).getTime(),
                                  max: new Date(maxDate).getTime()
                              },
                              y: {
                                min: minV,
                                max: maxV
                              }
                          },
                           plugins: {
                              tooltip: {
                                callbacks: {
                                    label: function(context) {
                                        const point = context.raw;
                                        return cachedLabel[point.x] ||[`Weight: ${point.y}`, ``, `${point.info}`];
                                    }
                                }
                              }
                           },
                           onHover: async function(event, chartElements) {
                                if (chartElements.length) {
                                    const activeElement = chartElements[0];
                                    const point = activeElement.element.$context.raw;
                                    const cacheKey = `${point.x}`;
                                    if(!cachedLabel[cacheKey]){
                                        var basicCircumference = await getBasicCircumference(cacheKey);
                                        var arrayToShow = await createArrayToShow(point.y, basicCircumference);
                                        cachedLabel[cacheKey] = arrayToShow;
                                        const tooltip = this.tooltip;
                                        tooltip.setActiveElements([{
                                            datasetIndex: activeElement.datasetIndex,
                                            index: activeElement.index
                                        }]);
                                        tooltip.update();
                                    }
                                    }

                           },
                           onClick: function(event) {
                                const activePoints = this.getElementsAtEventForMode(event, 'nearest', { intersect: true }, true);
                                if (activePoints.length > 0) {
                                const clickedPoint = activePoints[0].element.$context.raw;
                                const redirectUrl = `/body-data?date=${clickedPoint.x}`;
                                window.location.href = redirectUrl;
                                }
                           }
          }
        }
      );
    }

async function createChart() {
    var startDateFromInput = document.getElementById('startDate').value;
    var endDateFromInput = document.getElementById("endDate").value;
    var oneBefore = await getWeightBefore(startDateFromInput);
    var oneAfter = await getWeightAfter(endDateFromInput);
    var dateStart;
    var dateEnd;
    if(oneBefore!==null) {
        dateStart = oneBefore.date;
        }
    else {
        dateStart = startDateFromInput;
        }
    if(oneAfter!==null) {
        dateEnd = oneAfter.date;
        }
    else {
        dateEnd = endDateFromInput;
        }
    var weightList = await getWeightBetweenDates(dateStart, dateEnd);
    var weights = weightList.map(weight => weight.weightInKg);
    var datesForChart = generateRecordForDates(dateStart, dateEnd);
    var dataForChart = mapWeightOnDatesInChart(datesForChart, weightList);
    generateChart(dataForChart, calculateMinValueInChart(weights), calculateMaxValueInChart(weights),
                                                                startDateFromInput, endDateFromInput);
    }


(async function() {
        createChart();
    })();
