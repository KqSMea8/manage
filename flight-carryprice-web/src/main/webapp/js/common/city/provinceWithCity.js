/*
 *
 *省市联动js
 */
var province = document.getElementById('province'); 
var city = document.getElementById('city');
//省份   
var Provinces = new Array("--请选择--","");   
Provinces[0] = new Array("B-北京","北京");  
Provinces[1] = new Array("S-上海","上海");  
Provinces[2] = new Array("T-天津","天津");
Provinces[3] = new Array("C-重庆","重庆");  
Provinces[4] = new Array("H-河北","河北");  
Provinces[5] = new Array("S-山西","山西");  
Provinces[6] = new Array("L-辽宁","辽宁");  
Provinces[7] = new Array("J-吉林","吉林");  
Provinces[8] = new Array("H-黑龙江","黑龙江");   
Provinces[9] = new Array("J-江苏","江苏");   
Provinces[10] = new Array("Z-浙江","浙江");  
Provinces[11] = new Array("A-安徽","安徽");  
Provinces[12] = new Array("F-福建","福建");   
Provinces[13] = new Array("J-江西","江西");  
Provinces[14] = new Array("S-山东","山东");  
Provinces[15] = new Array("H-河南","河南");  
Provinces[16] = new Array("H-湖北","湖北");  
Provinces[17] = new Array("H-湖南","湖南");  
Provinces[18] = new Array("G-广东","广东");  
Provinces[19] = new Array("G-甘肃","甘肃");  
Provinces[20] = new Array("S-陕西","陕西");  
Provinces[21] = new Array("N-内蒙古自治区","内蒙古自治区");  
Provinces[22] = new Array("G-广西壮族自治区","广西壮族自治区");  
Provinces[23] = new Array("S-四川","四川");  
Provinces[24] = new Array("G-贵州","贵州");  
Provinces[25] = new Array("Y-云南","云南");  
Provinces[26] = new Array("X-西藏自治区","西藏自治区");  
Provinces[27] = new Array("H-海南","海南");  
Provinces[28] = new Array("N-宁夏回族自治区","宁夏回族自治区");  
Provinces[29] = new Array("Q-青海","青海");  
Provinces[30] = new Array("X-新疆维吾族自治区","新疆维吾族自治区");  
Provinces[31] = new Array("X-香港","香港");  
Provinces[32] = new Array("A-澳门","澳门");  
Provinces[33] = new Array("T-台湾","台湾");  
Provinces[34] = new Array("Q-其它","其它");  
 
 
 
 
 

var Citys = new Array(); 
Citys[0] = new Array("请选择", "请选择"); 
Citys[1] = new Array("北京", "B-北京"); 
Citys[2] = new Array("上海", "S-上海"); 
Citys[3] = new Array("天津", "T-天津"); 
Citys[4] = new Array("重庆", "C-重庆"); 
Citys[5] = new Array("河北", "S-石家庄","H-邯郸","X-邢台","B-保定","Z-张家口","C-承德","L-廊坊","T-唐山","Q-秦皇岛","C-沧州","H-衡水"); 
Citys[6] = new Array("山西", "T-太原","D-大同","Y-阳泉","C-长治","J-晋城","S-朔州","L-吕梁","Q-忻州","J-晋中","L-临汾","Y-运城"); 
Citys[7] = new Array("辽宁", "S-沈阳","D-大连","A-鞍山","F-抚顺","B-本溪","D-丹东","D-锦州","Y-营口","F-阜新","L-辽阳","P-盘锦","T-铁岭","C-朝阳","H-葫芦岛"); 
Citys[8] = new Array("吉林", "C-长春","J-吉林","S-四平","L-辽源","T-通化","B-白山","S-松原","B-白城","Y-延边"); 
Citys[9] = new Array("黑龙江", "H-哈尔滨","Q-齐齐哈尔","M-牡丹江","J-佳木斯","D-大庆","S-绥化","H-鹤岗","J-鸡西","H-黑河","S-双鸭山","Y-伊春","Q-七台河","D-大兴安岭"); 
Citys[10] = new Array("江苏", "N-南京","Z-镇江","S-苏州","N-南通","Y-扬州","Y-盐城","X-徐州","L-连云港","C-常州","W-无锡","S-宿迁","T-泰州","H-淮安"); 
Citys[11] = new Array("浙江", "H-杭州","N-宁波","W-温州","J-嘉兴","H-湖州","S-绍兴","J-金华","Q-衢州","Z-舟山","T-台州","L-丽水"); 
Citys[12] = new Array("安徽", "H-合肥","W-芜湖","B-蚌埠","M-马鞍山","H-淮北","T-铜陵","A-安庆","H-黄山","C-滁州","S-宿州","C-池州","H-淮南","C-巢湖","F-阜阳","L-六安","X-宣城","H-亳州"); 
Citys[13] = new Array("福建", "F-福州","X-厦门","P-莆田","S-三明","Q-泉州","Z-漳州","N-南平","L-龙岩","N-宁德"); 
Citys[14] = new Array("江西", "N-南昌","J-景德镇","J-九江","Y-鹰潭","P-萍乡","X-新馀","G-赣州","J-吉安","Y-宜春","F-抚州","S-上饶"); 
Citys[15] = new Array("山东", "J-济南","Q-青岛","Z-淄博","Z-枣庄","D-东营","Y-烟台","W-潍坊","J-济宁","T-泰安","W-威海","R-日照","L-莱芜","L-临沂","D-德州","L-聊城","B-滨州","H-菏泽"); 
Citys[16] = new Array("河南", "Z-郑州","K-开封","L-洛阳","P-平顶山","A-安阳","H-鹤壁","X-新乡","J-焦作","P-濮阳","X-许昌","L-漯河","S-三门峡","N-南阳","S-商丘","X-信阳","Z-周口","Z-驻马店","J-济源"); 
Citys[17] = new Array("湖北", "W-武汉","Y-宜昌","J-荆州","X-襄樊","H-黄石","J-荆门","H-黄冈","S-十堰","E-恩施","Q-潜江","T-天门","X-仙桃","S-随州","X-咸宁","X-孝感","E-鄂州"); 
Citys[18] = new Array("湖南", "C-长沙","C-常德","Z-株洲","X-湘潭","H-衡阳","Y-岳阳","S-邵阳","Y-益阳","L-娄底","H-怀化","C-郴州","Y-永州","X-湘西","Z-张家界"); 
Citys[19] = new Array("广东", "G-广州","S-深圳","Z-珠海","S-汕头","D-东莞","Z-中山","F-佛山","S-韶关","J-江门","Z-湛江","M-茂名","Z-肇庆","H-惠州","M-梅州","S-汕尾","H-河源","Y-阳江","Q-清远","C-潮州","J-揭阳","Y-云浮"); 
Citys[20] = new Array("甘肃", "L-兰州","J-嘉峪关","J-金昌","B-白银","T-天水","J-酒泉","Z-张掖","W-武威","D-定西","L-陇南","P-平凉","Q-庆阳","L-临夏","G-甘南"); 
Citys[21] = new Array("陕西", "X-西安","B-宝鸡","X-咸阳","T-铜川","W-渭南","Y-延安","Y-榆林","H-汉中","A-安康","S-商洛"); 
Citys[22] = new Array("内蒙古自治区", "H-呼和浩特","B-包头","W-乌海","J-集宁","T-通辽","C-赤峰","H-呼伦贝尔盟","A-阿拉善盟","Z-哲里木盟","X-兴安盟","W-乌兰察布盟","X-锡林郭勒盟","B-巴彦淖尔盟","Y-伊克昭盟"); 
Citys[23] = new Array("广西壮族自治区", "N-南宁","L-柳州","G-桂林","W-梧州","B-北海","F-防城港","Q-钦州","G-贵港","Y-玉林","L-柳州","H-贺州","B-百色","H-河池"); 
Citys[24] = new Array("四川", "C-成都","M-绵阳","D-德阳","Z-自贡","P-攀枝花","G-广元","N-内江","L-乐山","N-南充","Y-宜宾","G-广安","D-达川","Y-雅安","M-眉山","G-甘孜","L-凉山","L-泸州"); 
Citys[25] = new Array("贵州", "G-贵阳","L-六盘水","Z-遵义","A-安顺","T-铜仁","Q-黔西南","B-毕节","Q-黔东南","Q-黔南"); 
Citys[26] = new Array("云南", "K-昆明","D-大理","Q-曲靖","Y-玉溪","Z-昭通","C-楚雄","H-红河","W-文山","S-思茅","X-西双版纳","B-保山","D-德宏","L-丽江","N-怒江","D-迪庆","L-临沧"); 
Citys[27] = new Array("西藏自治区", "L-拉萨","R-日喀则","S-山南","L-林芝","C-昌都","A-阿里","N-那曲"); 
Citys[28] = new Array("海南", "H-海口","S-三亚"); 
Citys[29] = new Array("宁夏回族自治区", "Y-银川","S-石嘴山","W-吴忠","G-固原"); 
Citys[30] = new Array("青海", "X-西宁","H-海东","H-海南","H-海北","H-黄南","Y-玉树","G-果洛","H-海西"); 
Citys[31] = new Array("新疆维吾族自治区", "W-乌鲁木齐","S-石河子","K-克拉玛依","Y-伊犁","B-巴音郭勒","C-昌吉","K-克孜勒苏柯尔克孜","B-博尔塔拉","T-吐鲁番","H-哈密","K-喀什","H-和田","A-阿克苏","K-奎屯","T-塔城","A-阿勒泰","A-阿图什","K-库尔勒","Y-伊宁"); 
Citys[32] = new Array("香港", "X-香港"); 
Citys[33] = new Array("澳门", "A-澳门"); 
Citys[34] = new Array("台湾", "T-台北","G-高雄","T-台中","T-台南","P-屏东","N-南投","Y-云林","X-新竹","Z-彰化","M-苗栗","J-嘉义","H-花莲","T-桃园","Y-宜兰","J-基隆","T-台东","J-金门","M-马祖","P-澎湖"); 
Citys[35] = new Array("其它", "Q-其他省市地区","F-非中国城市地区");
 
 
function trim(str){ //删除左右两端的空格   
     return str.replace(/(^\s*)|(\s*$)/g, "");  
} 
 
 
//生成省份   
for(var key in Provinces) {  
        var provinceOption = document.createElement('option');  
        provinceOption.value = Provinces[key][1];
        provinceOption.text = Provinces[key][0];
        province.options.add(provinceOption);
        var hideprovince = document.getElementById('hideprovince');
        if(hideprovince != null){
           if(trim(hideprovince.value) == provinceOption.value){
              province.options[key].selected = true;
           }
        }
}  
//生成市   
//@current为当前选择的select节点，即父类节点   
//@child为子类点   
//@childArr为子节点数组   
function showChild(current, child, childArr) {  
        var currentValue = current.value;
        var count = childArr.length;  
        child.length = 1;  
        for(var i = 0; i < count; i++) {  
                //判断所选的省份，与当前节点第一个数组元素是否相同   
                if(currentValue == childArr[i][0]) {  
                        //不取第一个数组元素，因为第一个是父类，所以j从1开始   
                        for(var j = 1; j < childArr[i].length; j++) {  
                               var childOption = document.createElement('option');  
                               var value = childArr[i][j];   
                               childOption.value = value.substring(2);
                               childOption.text = childArr[i][j];  
                               child.options.add(childOption); 
                               var hidecity = document.getElementById('hidecity');
                               if(hidecity != null){
	                               if(trim(hidecity.value) == childOption.value){
	                                  child.options[j].selected = true;
	                               }  
                               }
                        }  
                }  
        }
        document.getElementById("city").options.remove(0); 
}  
//省份改变市   
province.onchange = function() {  
        showChild(province, city, Citys);  
}

  
 