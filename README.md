# youth

#### 介绍
基础项目模板，用于学习使用

#### 软件架构
说明： 
切记用户的敏感信息不可存入token中，token只存放用户的基本信息，如id，username等 
本系统采用双token认证，需要前端配合使用 分别为accessToken和refreshToken，accessToken用于访问资源，refreshToken用于刷新accessToken 用户登录时，后端返回accessToken和refreshToken双令牌 调用接口时，前端需要将accessToken放在请求头中，格式为自定义 若accessToken过期，前端需要使用refreshToken调用刷新接口，获取新的accessToken

