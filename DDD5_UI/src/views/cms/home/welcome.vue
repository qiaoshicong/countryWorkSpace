<template>
  <div class="box">
    <div class="container">
      <div class="cloud front">
        <span class="left-front"></span>
        <span class="right-front"></span>
      </div>
      <span class="sun sunshine"></span>
      <span class="sun"></span>
      <div class="cloud back">
        <span class="left-back"></span>
        <span class="right-back"></span>
      </div>
    </div>

    <div class="card-header">
      <span>欢迎使用本系统,请先登录↘↘↘</span>
<!--      <span></span>-->
      <span>March 13</span>
    </div>

    <span class="temp">23°</span>

    <div class="temp-scale">
<!--      <span>Celcius</span>-->
      <a-button type="text" @click="disappear()">登录</a-button>
    </div>
  </div>
<!--  <button class="huanying" v-if="loginbtn.flag">hjeziefhgvuer<button @click="disappear()">登录</button></button>-->
</template>

<script>

import {defineComponent, reactive} from "vue";
import {useRouter} from "vue-router";


export default defineComponent({

  setup() {
    const router = useRouter();
    const loginbtn = reactive({
      flag:true
    });

    const disappear =() =>{
      loginbtn.flag = false
      router.push({
        path: '/index/First',
        query: {
          msg: 'index'
        }
      })
    }

    return {

      disappear,
      loginbtn
    };
  }
})
</script>

<style scoped>
.box {
  width: 940px;
  height: 235px;
  position: relative;
  margin: 0 auto;
  padding: 25px;
  background: radial-gradient(178.94% 106.41% at 26.42% 106.41%, #FFF7B1 0%, rgba(255, 255, 255, 0) 71.88%) /* warning: gradient uses a rotation that is not supported by CSS and may not behave as expected */, #FFFFFF;
  box-shadow: 0px 155px 62px rgba(0, 0, 0, 0.01), 0px 87px 52px rgba(0, 0, 0, 0.05), 0px 39px 39px rgba(0, 0, 0, 0.09), 0px 10px 21px rgba(0, 0, 0, 0.1), 0px 0px 0px rgba(0, 0, 0, 0.1);
  border-radius: 23px;
  transition: all 0.8s cubic-bezier(0.15, 0.83, 0.66, 1);
  cursor: pointer;
}

.card:hover {
  transform: scale(1.05);
}

.container {
  width: 250px;
  height: 250px;
  position: absolute;
  right: -35px;
  top: -50px;
  display: flex;
  align-items: center;
  justify-content: center;
  transform: scale(0.7);
}

.cloud {
  width: 250px;
}

.front {
  padding-top: 45px;
  margin-left: 25px;
  display: inline;
  position: absolute;
  z-index: 11;
  animation: clouds 8s infinite;
  animation-timing-function: ease-in-out;
}

.back {
  margin-top: -30px;
  margin-left: 150px;
  z-index: 12;
  animation: clouds 12s infinite;
  animation-timing-function: ease-in-out;
}

.right-front {
  width: 45px;
  height: 45px;
  border-radius: 50% 50% 50% 0%;
  background-color: #4c9beb;
  display: inline-block;
  margin-left: -25px;
  z-index: 5;
}

.left-front {
  width: 65px;
  height: 65px;
  border-radius: 50% 50% 0% 50%;
  background-color: #4c9beb;
  display: inline-block;
  z-index: 5;
}

.right-back {
  width: 50px;
  height: 50px;
  border-radius: 50% 50% 50% 0%;
  background-color: #4c9beb;
  display: inline-block;
  margin-left: -20px;
  z-index: 5;
}

.left-back {
  width: 30px;
  height: 30px;
  border-radius: 50% 50% 0% 50%;
  background-color: #4c9beb;
  display: inline-block;
  z-index: 5;
}

.sun {
  width: 120px;
  height: 120px;
  background: -webkit-linear-gradient(to right, #fcbb04, #fffc00);
  background: linear-gradient(to right, #fcbb04, #fffc00);
  border-radius: 60px;
  display: inline;
  position: absolute;
}

.sunshine {
  animation: sunshines 2s infinite;
}

@keyframes sunshines {
  0% {
    transform: scale(1);
    opacity: 0.6;
  }

  100% {
    transform: scale(1.4);
    opacity: 0;
  }
}

@keyframes clouds {
  0% {
    transform: translateX(15px);
  }

  50% {
    transform: translateX(0px);
  }

  100% {
    transform: translateX(15px);
  }
}

.card-header {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin:40px auto;
}

.card-header span:first-child {
  word-break: break-all;
  font-weight: 800;
  font-size: 30px;
  margin-left: 200px;
  line-height: 135%;
  color: rgba(87, 77, 51, 0.66);
}


.card-header span:last-child {
  font-weight: 700;
  font-size: 15px;
  line-height: 135%;
  color: rgba(87, 77, 51, 0.33);
}

.temp {
  position: absolute;
  left: 25px;
  bottom: 12px;
  font-weight: 700;
  font-size: 64px;
  line-height: 77px;
  color: rgba(87, 77, 51, 1);
}

/*.temp-scale {*/
/*  width: 80px;*/
/*  height: 36px;*/
/*  position: absolute;*/
/*  right: 25px;*/
/*  bottom: 25px;*/
/*  display: flex;*/
/*  align-items: center;*/
/*  justify-content: center;*/
/*  background: rgba(0, 0, 0, 0.06);*/
/*  border-radius: 9px;*/
/*}*/

.temp-scale button {
    height: 45px;
    position: absolute;
    right: 25px;
    bottom: 25px;
background-color:rgba(0, 0, 0, 0.06) ;
  font-weight: 700;
  font-size: 33px;
  line-height: 134.49%;
  border-style: none;
  color: rgba(87, 77, 51, 0.66);
}
</style>
