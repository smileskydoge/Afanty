//随机生成工号
const randomStaffInfo =  (type='man',idLength=100,idCount=10) => {
  const staffInfo = [];
  for (var i = 0; i < idCount; i++) {
    const RandomId = parseInt(Math.random()*idLength);
    staffInfo.push({});
    staffInfo[i][RandomId] = `${type}${RandomId}`;
  }
  return staffInfo;
}

//autoCheck2SortStaffId: 合并两个部门员工信息;
//staff1基准合并;
const autoCheck2SortStaffId = (staff1,staff2)=>{

  //按照工号排序
  const sortStaffId = (dataArr)=>{
    const sortData =  dataArr.sort((pre,next)=>{
      const id = (obj)=> Number(Object.keys(obj)[0]);
      return id(pre)-id(next) > 0;
    });
    return sortData;
  };

  //数组换成json格式
  const jsonStaffInfo = (data)=>{
    const staffInfo = {};
    const length = data.length;
    for (variable of data) {
      Object.assign(staffInfo,variable)
    }
    return staffInfo;
  };

  //检查工号是否存在,存在+1返回newWorkID
  const isExistStaffId = (id,allStaffId)=>{
    let flag = allStaffId.indexOf(id)!=-1;
    let newStaffId = id;
    if(flag){
      newStaffId = (Number(newStaffId)+1).toString();
      return isExistStaffId(newStaffId,allStaffId)
    }else {
      return newStaffId;
    }
  };

  //产生新的工号,改变原始数据工号;
  const newWomanWorkId = (data,compareData)=>{
    const allStaffId = Object.keys(compareData);
    data.forEach((v,index)=>{
      const id = Object.keys(v)[0];
      const newStaffId = isExistStaffId(id,allStaffId);
      if(newStaffId != id){
          allStaffId.push(newStaffId);
          const newWorkInfo = {};
          newWorkInfo[newStaffId] = v[id];
          data[index] = newWorkInfo;
      }
    })
  };

  const staffInf = jsonStaffInfo(staff1);
  newWomanWorkId(staff2,staffInf);
  const allStaffInfo = [...staff1,...staff2];
  const sortAllStaffInfo = sortStaffId(allStaffInfo);
  return jsonStaffInfo(sortAllStaffInfo);
}

//比较极端例子,女员工工号全部重复与男员工工号,工号加1后又重复与后面女员工工号
// const manWorkId = [
//   { '14': 'man14' },
//   { '70': 'man70' },
//   { '15': 'man15' },
//   { '33': 'man33' },
//   { '26': 'man26' },
//   { '74': 'man74' },
//   { '39': 'man39' },
//   { '68': 'man68' },
//   { '94': 'man94' },
//   { '55': 'man55' }
// ];
// const womanWorkId = [
//   { '14': 'wonman14' },
//   { '70': 'wonman70' },
//   { '15': 'wonman15' },
//   { '33': 'wonman33' },
//   { '26': 'wonman26' },
//   { '74': 'wonman74' },
//   { '39': 'wonman39' },
//   { '68': 'wonman68' },
//   { '94': 'wonman94' },
//   { '55': 'wonman55' }
// ];
const manWorkId = randomStaffInfo();
const womanWorkId = randomStaffInfo('woman');
const mergeStaffInfo = autoCheck2SortStaffId(manWorkId,womanWorkId)

console.log(mergeStaffInfo);
