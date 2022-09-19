<template>
  <div class="board-detail">
    <div class="common-buttons">
      <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnPostDetails">이전페이지로</button>&nbsp;
    </div>
    <table class="w3-table-all">
      <thead>
      <tr>
        <th>택배회사</th>
        <th>운송장 번호</th>
        <th>보내는이</th>
        <th>받는이</th>
        <th>택배종류</th>
        <th>현위치</th>
        <th>택배회사업데이트일</th>
        <th>최근 업데이트 시간</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(row, idx) in list" :key="idx">
        <td>{{ row.postCompany }}</td>
        <td>{{ row.postNumber }}</td>
        <td>{{ row.sender }}</td>
        <td>{{ row.receiver }}</td>
        <td>{{ row.contentType }}</td>
        <td>{{ row.location }}</td>
        <td>{{ row.statusData}}</td>
        <td>{{ row.modifiedDate }}</td>
      </tr>
      </tbody>
    </table>
    <div class="common-buttons">
      <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnPostDetails">이전페이지로</button>&nbsp;
    </div>
  </div>
</template>

<script>
export default {
  name: "Recodes",
  components:{
  },
  data(){
    return{
      userId: this.$route.query.userId, //리스트 페이지 데이터전송
      list: {}, //리스트 데이터
    }
  },
  mounted() {
    this.fnGetList()
  },
  methods: {
    fnGetList() {
      this.requestBody = { // 데이터 전송
      }
      this.$axios.get(this.$homeserviceAPIUrl + "/"+this.userId+"/", {
      }).then((res) => {
        console.log(res.data);

        this.list = res.data.list  //서버에서 데이터를 목록으로 보내므로 바로 할당하여 사용할 수 있다.

      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    fnPostDetails(){
      this.$router.push({
        path: './',
      })
    }
  },
}
</script>