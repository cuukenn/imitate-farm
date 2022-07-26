package cn.jxufe.imp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import cn.jxufe.bean.Message;
import cn.jxufe.dao.CodeLandRequireDAO;
import cn.jxufe.dao.LandDAO;
import cn.jxufe.dao.LandViewDAO;
import cn.jxufe.dao.SeedBagDAO;
import cn.jxufe.dao.SeedDAO;
import cn.jxufe.dao.UserDAO;
import cn.jxufe.entity.CropsGrow;
import cn.jxufe.entity.Land;
import cn.jxufe.entity.Seed;
import cn.jxufe.entity.SeedBag;
import cn.jxufe.entity.User;
import cn.jxufe.service.CropsGrowService;
import cn.jxufe.service.FarmService;
import cn.jxufe.utils.JSONConfig;
import cn.jxufe.view.LandView;
import cn.jxufe.websocket.FarmActionHandler;
import net.sf.json.JSONArray;

@Service
public class FarmImp implements FarmService {

	@Autowired
	FarmActionHandler farmActionHandler;

	@Autowired
	LandViewDAO landViewDAO;

	@Autowired
	UserDAO userDAO;

	@Autowired
	CodeLandRequireDAO codeLandRequireDAO;

	@Autowired
	CodeLandRequireImp codeLandRequireImp;

	@Autowired
	LandDAO landDAO;

	@Autowired
	SeedDAO seedDAO;

	@Autowired
	SeedBagDAO seedBagDAO;

	@Autowired
	CropsGrowService cropsGrowService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.jxufe.service.FarmService#action(long,
	 * javax.servlet.http.HttpSession)
	 */
	@Override
	public Message action(long landId, HttpSession session) {
		Message result = new Message();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			result.setCode(-1);
			result.setMsg("用户不合法");
			return result;
		}
		Iterable<LandView> landView = landViewDAO.findByUId(user.getId());
		JSONArray array = JSONArray.fromObject(landView);
		try {
			farmActionHandler.sendMessageToUser(user.getId(), new TextMessage(array.toString()));
		} catch (Exception e) {
			result.setCode(-1);
			result.setMsg("消息发送失败！");
			return result;
		}
		result.setCode(0);
		result.setMsg("消息发送成功！");
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.jxufe.service.FarmService#actionPlant(long, long,
	 * javax.servlet.http.HttpSession)
	 */
	@Override
	// 事务原因，视图在内无法查询到最新值，所以分开写
	public Message actionPlant(long landId, long cId, HttpSession session) {
		Message message = this.plantTansition(landId, cId, session);
		if (message.getCode() != 0)
			return message;
		User user = (User) session.getAttribute("user");
		LandView landView = landViewDAO.findByUIdAndLandId(user.getId(), landId);
		List<LandView> arrayList = new ArrayList<LandView>();
		arrayList.add(landView);

		// 格式化时间转JSON输出
		JSONArray array = JSONArray.fromObject(arrayList, JSONConfig.getJsonConfig());
		farmActionHandler.sendMessageToUser(user.getId(), new TextMessage(array.toString()));

		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.jxufe.service.FarmService#actionKillWorm(long,
	 * javax.servlet.http.HttpSession)
	 */
	@Override
	// 事务原因，视图在内无法查询到最新值，所以分开写
	public Message actionKillWorm(long landId, HttpSession session) {
		Message message = this.killWormTansition(landId, session);
		if (message.getCode() != 0)
			return message;
		User user = (User) session.getAttribute("user");
		LandView landView = landViewDAO.findByUIdAndLandId(user.getId(), landId);
		ArrayList<LandView> arrayList = new ArrayList<>();
		arrayList.add(landView);
		JSONArray array = JSONArray.fromObject(arrayList, JSONConfig.getJsonConfig());
		farmActionHandler.sendMessageToUser(user.getId(), new TextMessage(array.toString()));
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.jxufe.service.FarmService#actionHarvest(long,
	 * javax.servlet.http.HttpSession)
	 */
	@Override
	public Message actionHarvest(long landId, HttpSession session) {
		Message message = this.harvestTansition(landId, session);
		if (message.getCode() != 0)
			return message;
		User user = (User) session.getAttribute("user");
		LandView landView = landViewDAO.findByUIdAndLandId(user.getId(), landId);
		if (landView == null) {
			landView = new LandView();
			landView.setLandId(landId);
		}

		ArrayList<LandView> arrayList = new ArrayList<>();
		arrayList.add(landView);
		JSONArray array = JSONArray.fromObject(arrayList, JSONConfig.getJsonConfig());
		farmActionHandler.sendMessageToUser(user.getId(), new TextMessage(array.toString()));
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.jxufe.service.FarmService#actionCleanLand(long,
	 * javax.servlet.http.HttpSession)
	 */
	@Override
	public Message actionCleanLand(long landId, HttpSession session) {
		Message message = this.cleanLandTansition(landId, session);
		if (message.getCode() != 0)
			return message;
		User user = (User) session.getAttribute("user");
		LandView landView = landViewDAO.findByUIdAndLandId(user.getId(), landId);
		if (landView == null) {// 解决无下季时无法伤=删除BUG，表现在JSONARAY转换失败
			Land land = new Land();
			land.setLandId(landId);
			ArrayList<Land> arrayList = new ArrayList<>();
			arrayList.add(land);
			JSONArray array = JSONArray.fromObject(arrayList, JSONConfig.getJsonConfig());
			TextMessage textMessage = new TextMessage(array.toString());
			farmActionHandler.sendMessageToUser(user.getId(), textMessage);
		} else {
			ArrayList<LandView> arrayList = new ArrayList<>();
			arrayList.add(landView);
			JSONArray array = JSONArray.fromObject(arrayList, JSONConfig.getJsonConfig());
			farmActionHandler.sendMessageToUser(user.getId(), new TextMessage(array.toString()));
		}

		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.jxufe.service.FarmService#plantTansition(long, long,
	 * javax.servlet.http.HttpSession)
	 */
	@Override
	@Transactional
	public Message plantTansition(long landId, long cId, HttpSession session) {
		Message result = new Message();
		try {
			if((int)landId>24 ||(int)landId<1) {
				result.setCode(-1);
				result.setMsg("土地范围为1~24");
				return result;
			}
			
			User user = (User) session.getAttribute("user");
			if (user == null) {
				result.setCode(-1);
				result.setMsg("用户不合法");
				return result;
			}

			Seed seed = seedDAO.findByCId(cId);
			if (seed == null) {
				result.setCode(-1);
				result.setMsg("不存在该种子！");
				return result;
			}

			int landRequirementCode = seed.getLandRequirement();
			boolean flag = codeLandRequireImp.isLandTypeSame(landId, landRequirementCode);
			if (!flag) {
				result.setCode(-1);
				result.setMsg("该种子与该土地类型不匹配！");
				return result;
			}

			SeedBag seedBag = seedBagDAO.findByCIdAndUId(cId, user.getId());
			if (seedBag.getcNumber() < 1) {
				result.setCode(-1);
				result.setMsg("该种子数量不足！");
				return result;
			}

			LandView landView = landViewDAO.findByUIdAndLandId(user.getId(), landId);
			if (landView != null) {
				result.setCode(-1);
				result.setMsg("该土地上已经存在植物");
				return result;
			}

			Land newLand = new Land();
			newLand.setId(0);
			newLand.setcId(cId);
			newLand.setLandId(landId);
			newLand.setuId(user.getId());

			CropsGrow cropsGrow = cropsGrowService.findFirstCrops(cId);
			if (cropsGrow == null) {
				result.setCode(-4);
				result.setMsg("该植物无生长阶段信息不全");
				return result;
			}
			newLand.setStatus(cropsGrow.getGrowStep());
			newLand = landDAO.save(newLand);

			// 初始化下季时间
			// landView = landViewDAO.findByUIdAndLandId(user.getId(), landId);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(newLand.getPlantTime());
			calendar.add(Calendar.SECOND, cropsGrow.getGrowTime());// 作为秒加入
			newLand.setCurCropsEndTime(calendar.getTime());
			landDAO.save(newLand);

			user.setExp(user.getExp() + 2);
			user.setPrice(user.getPrice() + 1);
			user.setScore(user.getScore() + 2);
			userDAO.save(user);

			if (seedBag.getcNumber() >= 2) {
				seedBag.setcNumber(seedBag.getcNumber() - 1);
				seedBagDAO.save(seedBag);
			} else {
				seedBagDAO.delete(seedBag);
			}

			result.setCode(0);
			result.setMsg("种植成功！");
		} catch (Exception e) {
			result.setCode(-1);
			result.setMsg("种植失败！");
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.jxufe.service.FarmService#killWormTansition(long,
	 * javax.servlet.http.HttpSession)
	 */
	@Override
	@Transactional
	public Message killWormTansition(long landId, HttpSession session) {
		Message result = new Message();
		try {
			User user = (User) session.getAttribute("user");
			if (user == null) {
				result.setCode(-1);
				result.setMsg("用户不合法");
				return result;
			}
			Land land = landDAO.findByLandIdAndUId(landId, user.getId());
			if (land == null) {
				result.setCode(-1);
				result.setMsg("该土地上不存在植物");
				return result;
			}

			if (land.getWorm() == 0) {
				result.setCode(-1);
				result.setMsg("该植物不存在害虫");
				return result;
			}
			land.setWorm(0);
			landDAO.save(land);

			user.setExp(user.getExp() + 2);
			user.setPrice(user.getPrice() + 1);
			user.setScore(user.getScore() + 2);
			userDAO.save(user);

			result.setCode(0);
			result.setMsg("杀虫收获<br/>经验：+2<br/>金币：+1<br/>积分：+2<br/>");

			return result;
		} catch (Exception e) {
			result.setCode(-1);
			result.setMsg("种植失败！");
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.jxufe.service.FarmService#harvestTansition(long,
	 * javax.servlet.http.HttpSession)
	 */
	@Override
	@Transactional
	public Message harvestTansition(long landId, HttpSession session) {
		Message result = new Message();
		try {
			User user = (User) session.getAttribute("user");
			if (user == null) {
				result.setCode(-1);
				result.setMsg("用户不合法");
				return result;
			}

			Land land = landDAO.findByLandIdAndUId(landId, user.getId());
			if (land == null) {
				result.setCode(-2);
				result.setMsg("该土地上不存在植物");
				return result;
			}

			LandView landView = landViewDAO.findByUIdAndLandId(user.getId(), landId);

			if (!landView.getCropsCaption().equals("成熟阶段")) {
				result.setCode(-3);
				result.setMsg("该土地上上的植物未到成熟阶段");
				return result;
			}

			user.setExp(user.getExp() + landView.getExp());
			if (landView.getLoss() >= landView.getOutput())
				landView.setLoss(landView.getOutput() >> 1);
			user.setPrice(user.getPrice() + landView.getPrice4UnitSale() * (landView.getOutput() - landView.getLoss()));
			user.setScore(user.getScore() + landView.getScore());
			userDAO.save(user);

			CropsGrow cropsGrow = cropsGrowService.findNextCrops(land.getcId(), landView.getGrowStep());
			if (cropsGrow == null) {
				result.setCode(-4);
				result.setMsg("该植物无生长阶段信息不全");
				return result;
			}
			land.setStatus(cropsGrow.getGrowStep());
			land = landDAO.save(land);

			result.setCode(0);
			result.setMsg("收获成功！经验：+" + landView.getExp() + "<br/>" + landView.getPrice4UnitSale() + "个金币×"
					+ (landView.getOutput() - landView.getLoss()) + "个果实="
					+ landView.getPrice4UnitSale() * (landView.getOutput() - landView.getLoss()) + "个金币<br/>积分：+"
					+ landView.getScore());
		} catch (Exception e) {
			result.setCode(-1);
			result.setMsg("收获失败！");
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.jxufe.service.FarmService#cleanLandTansition(long,
	 * javax.servlet.http.HttpSession)
	 */
	@Override
	@Transactional
	public Message cleanLandTansition(long landId, HttpSession session) {
		Message result = new Message();
		try {
			User user = (User) session.getAttribute("user");
			if (user == null) {
				result.setCode(-1);
				result.setMsg("用户不合法");
				return result;
			}

			Land land = landDAO.findByLandIdAndUId(landId, user.getId());
			if (land == null) {
				result.setCode(-1);
				result.setMsg("该土地上不存在植物");
				return result;
			}

			LandView landView = landViewDAO.findByUIdAndLandId(user.getId(), landId);

			// 存在下一季
			if (landView.getCurHarvestNum() < landView.getHarvestNum()) {
				CropsGrow cropsGrow = cropsGrowService.findFirstCrops(landView.getcId());
				if (cropsGrow == null) {
					result.setCode(-4);
					result.setMsg("该植物无生长阶段信息不全");
					return result;
				}
				land.setStatus(cropsGrow.getGrowStep());
				land.setCurHarvestNum(land.getCurHarvestNum() + 1);
				land.setLoss(0);
				land.setWorm(0);
				land.setPlantTime(new Date());

				// 更新下阶段结束时间
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(land.getPlantTime());
				calendar.add(Calendar.SECOND, cropsGrow.getGrowTime());// 作为秒加入
				land.setCurCropsEndTime(calendar.getTime());

				land = landDAO.save(land);
			}
			// 不存在下一季，铲除
			else {
				landDAO.delete(land);
			}

			user.setExp(user.getExp() + 5);
			user.setScore(user.getScore() + 5);
			userDAO.save(user);
			result.setCode(0);
			result.setMsg("除枯草收获<br/>经验：+5<br/>积分：+5<br/>");

		} catch (Exception e) {
			result.setCode(-1);
			result.setMsg("除草失败！");
		}
		return result;
	}
}