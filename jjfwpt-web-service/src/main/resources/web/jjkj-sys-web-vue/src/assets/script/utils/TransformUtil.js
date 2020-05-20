const parseTree = function (data,mapping) {
  let treeData=[];
  for(let i in data){
    let dataItem = data[i];
    for(let key in mapping){
      dataItem[key]=dataItem[mapping[key]];
    }
    treeData.push(dataItem);
    if(dataItem.children&&dataItem.children.length>0){
      parseTree(dataItem.children,mapping)
    }
  }
  return treeData;
}

export default {parseTree}
