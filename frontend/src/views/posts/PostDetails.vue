<template>
  <div class="board-detail">
    <div class="common-buttons">
      <h3>최근 업데이트 시간 {{modifiedDate}}</h3>
      <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnUpdate">새로고침</button>&nbsp;
      <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnMyRecodes">내 택배기록 조회</button>&nbsp;
    </div>
    <div class="board-contents">
      <h3>택배회사: {{ postCompany }}</h3>
      <h3>운송장 번호: {{ postNumber }}</h3>
      <h3>보내는이: {{ sender }}</h3>
      <h3>받는이: {{ receiver }}</h3>
      <h3>택배종류: {{ contentType }}</h3>
      <h3>현위치: {{ location }}</h3>
      <h3>택배회사업데이트일: {{ statusData }}</h3>
    </div>
    <div class="board-contents">
      <span>{{ message }}</span>
    </div>
    <div class="common-buttons">
      <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnUpdate">새로고침</button>&nbsp;
      <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnMyRecodes">내 택배기록 조회</button>&nbsp;
    </div>
  </div>
</template>

<script>
export default {
  name: "PostDetails",
  components: {},
  data() {
    return {
      requestBody: '', //boardlist에서 push한것 대한 쿼리 값!
      postCompany: '',
      postNumber:'',
      sender:'',
      receiver: '',
      contentType: '',
      message: '',
      location: '',
      statusData: '',
      modifiedDate: '',
    };
  },
  mounted() {
    this.fnGetPostDetails()
  },
  methods: {
    fnGetPostDetails() {
      this.requestBody = { // JSON 데이터 전송
      }
      this.$axios.get(
          this.$homeserviceAPIUrl + "/"+this.$route.params.userId+"/"+this.$route.params.postNumber+"/", {
        params: this.requestBody}
      ).then((res)=> {
        console.log(res); //서버
        this.postCompany= res.data.postCompany,
        this.postNumber= res.data.postNumber,
        this.sender= res.data.sender,
        this.receiver= res.data.receiver,
        this.contentType= res.data.contentType,
        this.message= res.data.message,
        this.location= res.data.location,
        this.statusData= res.data.statusData,
        this.modifiedDate= res.data.modifiedDate
      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
    })
  },
  fnUpdate(){
    this.postCompany ="CJ대한통운",
    this.$axios.post(
        this.$homeserviceAPIUrl + "/"+this.$route.params.userId+"/"+this.$route.params.postNumber+"/",
        {},{params: {"postCompany":this.postCompany}}
    ).then(()=> {
      alert("새로고침되었습니다.")
    }).catch((err)=> {
      console.log(err)
      alert("네트워크 오류 또는 정상적인 처리 못함(400일경우 택배회사오류)")
    })
  },
  fnMyRecodes(){
    this.$router.push({
      name: "Recodes",
      query: {userId: this.$router.params.userId}
    })
    }
  },
}
</script>
<style scoped>

</style>