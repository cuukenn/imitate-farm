package cn.jxufe.view;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.jxufe.bean.EntityID;

@Entity
@Table(name = "V_LandView")
public class LandView extends EntityID {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int growTime;// 阶段生长时间
	private float insect;// 长虫概率
	private long status;

	private String caption;// 种子名称
	private int width;// 宽度
	private int height;// 高度
	private int offsetX;// x偏移
	private int offsetY;// y偏移

	private int harvestNum;// 可收获季数
	private int exp;// 经验值
	private int output;// 产量
	private int score;// 积分
	private int price4UnitSale;// 售出单价

	private long uId;// 用户id
	private long landId;// 土地编号
	private long cId;// 种子Id

	private int worm;// 生虫
	private int loss;// 当前季损失
	private Date  plantTime;// 种值日期
	private int curHarvestNum;// 当前季数
	private int matureTime;// 每季成熟时间

	private String growCaption;// 生长阶段代号
	private int growStep;// 生长阶段

	private String cropsCaption;// 生长阶段名称
	
	private String landRequireCaption;//土地类型
	
	private Date curCropsEndTime;

	// 图片地址 不序列化
	@Transient
	private String imgUrl;

	public String getImgUrl() {
		switch (cropsCaption) {
		case "种子阶段":
			imgUrl = "basic/0.png";
			break;
		case "枯草阶段":
			imgUrl = "basic/9.png";
			break;
		default:
			imgUrl = cId + "/" + growStep + ".png";
			break;
		}
		return imgUrl;
	}

	public int getGrowTime() {
		return growTime;
	}

	public void setGrowTime(int growTime) {
		this.growTime = growTime;
	}

	public float getInsect() {
		return insect;
	}

	public void setInsect(float insect) {
		this.insect = insect;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	public int getHarvestNum() {
		return harvestNum;
	}

	public void setHarvestNum(int harvestNum) {
		this.harvestNum = harvestNum;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getOutput() {
		return output;
	}

	public void setOutput(int output) {
		this.output = output;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getPrice4UnitSale() {
		return price4UnitSale;
	}

	public void setPrice4UnitSale(int price4UnitSale) {
		this.price4UnitSale = price4UnitSale;
	}

	public long getuId() {
		return uId;
	}

	public void setuId(long uId) {
		this.uId = uId;
	}

	public long getLandId() {
		return landId;
	}

	public void setLandId(long landId) {
		this.landId = landId;
	}

	public long getcId() {
		return cId;
	}

	public void setcId(long cId) {
		this.cId = cId;
	}

	public int getWorm() {
		return worm;
	}

	public void setWorm(int worm) {
		this.worm = worm;
	}

	public int getLoss() {
		return loss;
	}

	public void setLoss(int loss) {
		this.loss = loss;
	}

	public Date getPlantTime() {
		return plantTime;
	}

	public void setPlantTime(Date plantTime) {
		this.plantTime = plantTime;
	}

	public int getCurHarvestNum() {
		return curHarvestNum;
	}

	public void setCurHarvestNum(int curHarvestNum) {
		this.curHarvestNum = curHarvestNum;
	}

	public int getMatureTime() {
		return matureTime;
	}

	public void setMatureTime(int matureTime) {
		this.matureTime = matureTime;
	}

	public String getGrowCaption() {
		return growCaption;
	}

	public void setGrowCaption(String growCaption) {
		this.growCaption = growCaption;
	}

	public int getGrowStep() {
		return growStep;
	}

	public void setGrowStep(int growStep) {
		this.growStep = growStep;
	}

	public String getCropsCaption() {
		return cropsCaption;
	}

	public void setCropsCaption(String cropsCaption) {
		this.cropsCaption = cropsCaption;
	}

	public String getLandRequireCaption() {
		return landRequireCaption;
	}

	public void setLandRequireCaption(String landRequireCaption) {
		this.landRequireCaption = landRequireCaption;
	}

	public Date getCurCropsEndTime() {
		return curCropsEndTime;
	}

	public void setCurCropsEndTime(Date curCropsEndTime) {
		this.curCropsEndTime = curCropsEndTime;
	}
	
	
}
