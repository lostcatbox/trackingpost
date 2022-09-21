<template>
  <div class="board-detail">
    <div class="common-buttons">
      <b-button type="button" pill variant="primary" v-on:click="fnUpdate">새로고침</b-button>&nbsp;
      <b-button type="button" pill variant="success" v-on:click="fnMyRecodes">내 택배기록 조회</b-button>&nbsp;
    </div>
    <br>
    <div class="board-contents">
      <b-table-simple hover small caption-top stacked>
        <b-thead head-variant="dark">
          <b-tr>
            <b-th>택배회사</b-th>
            <b-th>운송장 번호</b-th>
            <b-th>보내는이</b-th>
            <b-th>받는이</b-th>
            <b-th>택배종류</b-th>
            <b-th>현위치</b-th>
            <b-th>상세정보</b-th>
            <b-th>업데이트일</b-th>
          </b-tr>
        </b-thead>
        <b-tbody>
          <b-tr>
            <b-th>최근 업데이트 시간: {{modifiedDate}}</b-th>
            <b-th stacked-heading="목록" class="text-left">값</b-th>
            <b-td stacked-heading="택배회사" class="text-left">{{postCompany}}</b-td>
            <b-td stacked-heading="운송장 번호" class="text-left">{{ postNumber }}</b-td>
            <b-td stacked-heading="보내는이" class="text-left">{{ sender }}</b-td>
            <b-td stacked-heading="받는이" class="text-left">{{ receiver }}</b-td>
            <b-td stacked-heading="택배종류" class="text-left">{{ contentType }}</b-td>
            <b-td stacked-heading="현위치" class="text-left">{{ location }}</b-td>
            <b-td stacked-heading="상세정보" class="text-left">{{ message }}</b-td>
            <b-td stacked-heading="업데이트일" class="text-left">{{ statusData }}</b-td>
          </b-tr>
        </b-tbody>
      </b-table-simple>
    </div>
    <div class="common-buttons">
      <b-button type="button" pill variant="primary" v-on:click="fnUpdate">새로고침</b-button>&nbsp;
      <b-button type="button" pill variant="success" v-on:click="fnMyRecodes">내 택배기록 조회</b-button>&nbsp;
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
        if (!res.data.postCompany){
          alert("택배송장번호가 잘못되었거나, 택배사에 아직 조회되지 않는 택배입니다")
        }
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
      //테스트환경에서는 postCompany임의로 넣어주기
    this.$axios.post(
        this.$homeserviceAPIUrl + "/"+this.$route.params.userId+"/"+this.$route.params.postNumber+"/",
        {},{
          params: {"postCompany":this.postCompany},
          headers: {  'Content-type': 'application/x-www-form-urlencoded; charset=UTF-8',
            'Accept': '*/*'}
        }
    ).then(()=> {
      alert("새로고침되었습니다.")
      this.fnGetPostDetails()
    }).catch((err)=> {
      console.log(err)
      alert("네트워크 오류 또는 정상적인 처리 못함(400일경우 택배회사오류)")
    })
  },
  fnMyRecodes(){
    this.$router.push({
      path: '/recodes',
      query: {userId: this.$route.params.userId}
    })
    }
  },
}
</script>
<style scoped>

</style>