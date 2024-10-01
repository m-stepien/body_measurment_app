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

()=>{
    var a = await getFirstWeightData();
}