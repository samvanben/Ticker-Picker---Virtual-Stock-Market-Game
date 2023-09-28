<template>
<div id="error">
    <h1>404</h1>
    <h2>Oops! It seems you've invested in some stock that doesn't exist. Let's get you back to the trading floor!</h2>
</div>
</template>

<script>
export default {
    name: "NotFound",
}
</script>


<style>

#error {
    display: grid;
    background-color: rgba(69, 69, 69, 0.75);
    border-radius: 20px;
    width: auto;
    height: auto;
    box-shadow: 12px 12px 10px black;
    margin-left: 500px;
    margin-right: 500px;
    margin-top: 10px;
}
h1 {
    color: rgba(255, 0, 0, 0.849);
    text-align: center;
    font-family:Verdana, Geneva, Tahoma, sans-serif;
    font-size: 1000%;
    margin-bottom: -15px;
    margin-top: -5px
    
}
h2 {
    color: white;
    text-align: center;
    font-family:Verdana, Geneva, Tahoma, sans-serif;
    padding: 5px
}
  
  
</style>