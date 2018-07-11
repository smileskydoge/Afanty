
const arr = [1,2,"1",null,null,undefined,undefined,false,false,NaN,NaN];

//1.0 遍历 无法判断NaN

//for循环去重,效率较高
const unique1 = (arr)=>{
  const newArr = [];
  let item;
  for (let i = 0 , len = arr.length ; i < len; i++) {
    item = arr[i];
    if(newArr.indexOf(item) === -1){
      newArr.push(item);
    }
  }
  return newArr;
}
console.log('遍历:unique1',unique1(arr));

//forEach  效率不如for,判断不出NaN
const unique2 = (arr)=>{
  const newArr = [];
  arr.forEach(item=>{
    if(newArr.indexOf(item) === -1){
      newArr.push(item);
    }
  })
  return newArr;
}
console.log('遍历:unique2',unique2(arr));

//reduc
const unique3 = (arr)=>{
  return arr.reduce((pre,next)=>{
    if(pre.indexOf(next) === -1){
      pre.push(next)
    };
    return pre;
  },[])
}
console.log('遍历:unique3',unique3(arr));
//解决NaN无法排除问题
const unique1_1 = (arr)=>{
  const newArr = [];
  let item;
  let flag = true;
  for (let i = 0 , len = arr.length ; i < len; i++) {
    item = arr[i];
    if(newArr.indexOf(item) === -1){
      if(item != item){ //排除NaN
        if(flag){
          newArr.push(item);
          flag = false;
        }
      }else {
        newArr.push(item);
      }
    }
  }
  return newArr;
}

console.log('解决NaN问题:unique1_1',unique1_1(arr));


//2.0 索引去重 把NaN直接全部去除掉了
//2.1for
 const unique4 = (arr)=>{
   const newArr = [];
   let item;
   for (let i = 0 , len = arr.length; i < len; i++) {
     item = arr[i];
     if(arr.indexOf(item) === i){
       newArr.push(item);
     }
   }
   return newArr;
 }
 console.log('索引去重:unique4',unique4(arr));


//2.2filter
const unique5 = (arr)=>{
  return arr.filter((item,index,arr)=>arr.indexOf(item) === index)
}
console.log('索引去重:unique4',unique5(arr));

//3.0集合转换 效率低,能去掉重复NaN;
//Set
const unique6 = (arr)=>{
  return Array.from(new Set(arr));
}
console.log('集合转换:unique6',unique6(arr));


//挂载方法到Array上
const unique = function(){
  return Array.from(new Set(this));
}
Array.prototype.unique = unique;
const uniqueArr = arr.unique()
console.log('Array增加unique方法',uniqueArr);
