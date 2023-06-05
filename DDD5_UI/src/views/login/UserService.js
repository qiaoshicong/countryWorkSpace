import axios from "axios";

/*
用户信息
 */
export class UserService{
  //修改邮箱和密码
  static doSubmit (email,password){
    const query={
      email,password
    }

    return axios({
      url:'/system/user/doSubmit',
      method: 'POST',
      params: query,
    })
  }

  static userRegister (deleted,email,email_verified,nickname,password,state,username){
    const query={
      deleted,email,email_verified,nickname,password,state,username
    }

    return axios({
      url:'/sys/user/userRegister',
      method: 'POST',
      params: query,
    })
  }

  //修改密码
  static updatePassword (email,password){
    const query={
      email,password
    }

    return axios({
      url:'/sys/user/updatePassword',
      method: 'POST',
      params: query,
    })
  }

  //通过用户id返回eid
  static getUserEidById (username) {
    const query = {
      username
    }
    return axios({
      url: '/sys/user/getUserEidById',
      method: 'POST',
      params: query,
    })
  }

  // 通过用户邮箱返回eid
  static getUserEidByEmail (email) {
    const query = {
      email
    }
    return axios({
      url: '/sys/user/getUserEidByEmail',
      method: 'POST',
      params: query,
    })
  }

  //通过姓名返回eid
  static getUserEid (username) {
    const query = {
      username
    }
    return axios({
      url: '/sys/user/getUserEid',
      method: 'POST',
      params: query,
    })
  }

  //存入user_role表
  static insertBatchSingle (userId,roleId) {
    const query = {
      userId,
      roleId
    }
    return axios({
      url: '/sys/user/insertBatchSingle',
      method: 'POST',
      params: query,
    })
  }


}
