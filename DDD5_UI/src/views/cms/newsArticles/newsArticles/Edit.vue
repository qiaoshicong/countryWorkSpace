<!--<template>-->
<!--  <div class='box'>-->
<!--    <el-row-->
<!--    ><h3 style='margin-top: 0px'>{{ $route.query.title }}</h3></el-row-->
<!--    >-->
<!--    新闻标题：-->
<!--    <el-input v-model='title' placeholder='请输入新闻标题'></el-input>-->
<!--    <br /><br />-->
<!--    发布日期：-->
<!--    <el-date-picker format='yyyy 年 MM 月 dd 日' value-format='yyyy-MM-dd' v-model='releaseTime'-->
<!--                    placeholder='请选择新闻发布日期'></el-date-picker>-->
<!--    <br /><br />-->
<!--    新闻内容：-->
<!--    <quill-editor ref='text' v-model='content' class='myQuillEditor' :options='editorOption' />-->
<!--    <el-row style='margin-top:50px;'>-->
<!--      <el-button type='success' @click='submit'>发布</el-button>-->
<!--    </el-row>-->
<!--  </div>-->
<!--</template>-->
<!--<script>-->
<!--// import axios from 'axios'-->


<!--Quill.register('modules/ImageExtend', ImageExtend)-->
<!--// axios.defaults.withCredentials = true-->

<!--export default {-->
<!--  components: { quillEditor },-->
<!--  data() {-->
<!--    return {-->
<!--      title: '', // 标题内容-->
<!--      releaseTime: '', // 发布日期-->
<!--      content: '', // 新闻内容-->
<!--      picture: null,-->
<!--      photo: '',-->
<!--      newsCategoryId: 2,-->
<!--      editorOption: {-->
<!--        placeholder: '请输入正文',-->
<!--        modules: {-->
<!--          ImageExtend: {-->
<!--            // 如果不作设置，即{}  则依然开启复制粘贴功能且以base64插入-->
<!--            name: 'file', // 图片参数名-->
<!--            loading: true,-->
<!--            size: 10, // 可选参数 图片大小，单位为M，1M = 1024kb-->
<!--            // todo 端口10086-->
<!--            //action: 'http://localhost:10086/uploading',-->
<!--            //action: 'http://43.138.103.28:10086/uploading',-->
<!--            action: 'http://localhost:10086/uploading',-->
<!--            // todo 端口10086-->
<!--            response: res => {-->
<!--              console.log(res)-->
<!--              //return 'http://localhost:10086/' + res.data-->
<!--              return 'http://localhost:10086/' + res.data-->
<!--              //return 'http://43.138.103.28:10086/' + res.data-->
<!--            },-->
<!--            headers: xhr => {-->
<!--              // axios.defaults.withCredentials = true;-->
<!--              // xhr.setRequestHeader('Cookie','NMTID=00OVM6QOJcTDVhqDUtvgSnd-5FBxLcAAAF9nTiDHg; JSESSIONID=5ACC52D29A2434E09450CB975E08C288')-->
<!--            }, // 可选参数 设置请求头部-->
<!--            sizeError: () => {-->
<!--            }, // 图片超过大小的回调-->
<!--            start: () => {-->
<!--            }, // 可选参数 自定义开始上传触发事件-->
<!--            end: () => {-->
<!--            }, // 可选参数 自定义上传结束触发的事件，无论成功或者失败-->
<!--            error: () => {-->
<!--            }, // 可选参数 上传失败触发的事件-->
<!--            success: () => {-->
<!--            }, // 可选参数  上传成功触发的事件-->
<!--            change: (xhr, formData) => {-->
<!--              // formData.append('token', 'myToken')-->
<!--            } // 可选参数 每次选择图片触发，也可用来设置头部，但比headers多了一个参数，可设置formData-->
<!--          },-->
<!--          toolbar: {-->
<!--            container: [-->
<!--              ['bold', 'italic', 'underline', 'strike'], // 加粗，斜体，下划线，删除线-->
<!--              ['blockquote', 'code-block'], // 引用，代码块-->
<!--              [{ header: 1 }, { header: 2 }], // 标题，键值对的形式；1、2表示字体大小-->
<!--              [{ list: 'ordered' }, { list: 'bullet' }], // 列表-->
<!--              [{ script: 'sub' }, { script: 'super' }], // 上下标-->
<!--              [{ indent: '-1' }, { indent: '+1' }], // 缩进-->
<!--              [{ direction: 'rtl' }], // 文本方向-->
<!--              [{ size: ['small', false, 'large', 'huge'] }], // 字体大小-->
<!--              [{ header: [1, 2, 3, 4, 5, 6, false] }], // 几级标题-->
<!--              [{ color: [] }, { background: [] }], // 字体颜色，字体背景颜色-->
<!--              [{ font: [] }], // 字体-->
<!--              [{ align: [] }], // 对齐方式-->
<!--              ['clean'], // 清除字体样式-->
<!--              ['image'] // 上传图片、上传视频-->
<!--            ],-->
<!--            handlers: {-->
<!--              image: function() {-->
<!--                // 劫持原来的图片点击按钮事件-->
<!--                QuillWatch.emit(this.quill.id)-->
<!--              }-->
<!--            }-->
<!--          }-->
<!--        }-->
<!--      }, // 编辑器新闻对象-->
<!--      fileList: []-->
<!--    }-->
<!--  },-->
<!--  created() {-->
<!--    // console.log('>>>>>>>',this.$route.query.id);-->
<!--    this.newsCategoryId = this.$route.query.id-->
<!--  },-->
<!--  mounted() {-->
<!--  },-->
<!--  methods: {-->
<!--    submit() {-->
<!--      const data = {-->
<!--        newsCategoryId: this.newsCategoryId,-->
<!--        content: this.content,-->
<!--        title: this.title,-->
<!--        releaseTime: this.releaseTime-->
<!--      }-->
<!--      console.log(this.content)-->
<!--      add(data).then(res => {-->
<!--        console.log(res)-->
<!--        if (res.code !== 200) {-->
<!--          return this.$message.error('发布新闻失败，请重试！')-->
<!--        } else {-->
<!--          this.$message.success('新闻发布成功！')-->
<!--          console.log(this.content)-->
<!--          this.$router.go(-1)-->
<!--        }-->
<!--      })-->
<!--    }-->
<!--  }-->
<!--}-->
<!--</script>-->

<!--<style lang='less' scoped>-->
<!--.box {-->
<!--  width: 1200px;-->
<!--  margin: 0 auto;-->
<!--}-->

<!--.myQuillEditor {-->
<!--  height: 350px;-->
<!--}-->

<!--.btn {-->
<!--  position: absolute;-->
<!--  bottom: 20px;-->
<!--  left: 168px;-->
<!--}-->

<!--.ql-editor img {-->
<!--  width: 100px !important;-->
<!--  height: 100px !important;-->
<!--}-->
<!--</style>-->
