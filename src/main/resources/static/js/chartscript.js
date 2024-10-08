let currentChart;
let cachedLabel = {};

const ctx = document.getElementById('weightChart').getContext('2d');
    async function getWeightBefore(start){
        const url= 'http://localhost:8080/weight/get/before?date='+encodeURIComponent(start);
        try {
            const response = await fetch(url);
            if (!response.ok) {
              throw new Error(`Response status: ${response.status}`);
            }
            const weight = await response.json();
            console.log("Data fetched successfully:", weight);
            if(weight===null || weight.date===null){
            return null;
            }
            else{
            return weight;
            }
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
            if(weight===null || weight.date===null){
                return null;
            }
            else{
                return weight;
            }
          } catch (error) {
                console.error(error.message);
            return null;
          }
    }

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

    function generateRecordForDates(startDateStr, endDateStr){
        const dates = []
        let currentDate = new Date(startDateStr);
        let endDate = new Date(endDateStr);
        while(currentDate<=endDate){
            dates.push(new Date(currentDate));
            currentDate.setDate(currentDate.getDate() + 1);
        }
        return dates;
    }

    async function getBasicCircumference(date){
        const url = 'http://localhost:8080/bodyMonitoring/getBasicCircumferece?date='+encodeURIComponent(date);
        try {
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error(`Response status: ${response.status}`);
            }
            const basicCircumference = await response.json();
            if(basicCircumference===null || basicCircumference.id===null){
                return null;
            }
            else{
                console.log("Data fetched successfully:", basicCircumference);
                return basicCircumference;
            }
        } catch (error) {
            console.error(error.message);
            return null;
        }
    }

    async function createArrayToShow(weightStr, basicCircumference){
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


    async function createChart(){
        var startDateFromInput = document.getElementById('startDate').value;
        var endDateFromInput = document.getElementById("endDate").value;
        var oneBefore = await getWeightBefore(startDateFromInput);
        var oneAfter = await getWeightAfter(endDateFromInput);
        var dateStart;
        var dateEnd;
        if(oneBefore!==null){
            dateStart = oneBefore.date;
        }
        else{
            dateStart = startDateFromInput;
        }
        if(oneAfter!==null){
            dateEnd = oneAfter.date;
        }
        else{
            dateEnd = endDateFromInput;
        }
        var weightList = await getWeightBetweenDates(dateStart, dateEnd);
        var weights = weightList.map(weight => weight.weightInKg);
        var datesForChart = generateRecordForDates(dateStart, dateEnd);
        var dataForChart = mapWeightOnDatesInChart(datesForChart, weightList);
        generateChart(dataForChart, calculateMinValueInChart(weights), calculateMaxValueInChart(weights), startDateFromInput, endDateFromInput);
    }

    (async function(){
        createChart();
    })();
