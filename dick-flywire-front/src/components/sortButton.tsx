
function SortButton(inputValue: string, requiredValue: string, sortPref: string) {
    
  if(inputValue === requiredValue && sortPref === "desc"){
    return "V";
  }else if(inputValue === requiredValue && sortPref === "asc"){
    return "^";
  }else{
    return "";
  }
}  
export default SortButton;