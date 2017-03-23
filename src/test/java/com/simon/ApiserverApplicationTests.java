package com.simon;

import com.simon.domain.*;
import com.simon.repository.AppUserRepository;
import com.simon.repository.MultipleChoiceRepository;
import com.simon.repository.SinglePaperRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiserverApplicationTests {

	@Autowired
	private MultipleChoiceRepository multipleChoiceRepository;

	@Autowired
	private SinglePaperRepository singlePaperRepository;

	@Autowired
	private AppUserRepository appUserRepository;

	private final static Logger log = LoggerFactory.getLogger(ApiserverApplicationTests.class);

	@Test
	public void contextLoads() {
	}

	/*@Test
	public void saveChoiceAndPaper(){
		MultiChoice singleChoice = new MultiChoice();
		singleChoice.setQuestion("C语言程序是由______构成的");
		ArrayList<ChoiceItem> choices = new ArrayList<>();
		choices.add(new ChoiceItem("一些可执行语言", false));
		choices.add(new ChoiceItem("main函数", false));
		choices.add(new ChoiceItem("函数", true));
		choices.add(new ChoiceItem("包含文件的第一个函数", false));
		singleChoice.setChoiceItems(choices);
		multipleChoiceRepository.save(singleChoice);

		ChoicePaper choicePaper = new ChoicePaper();

		ArrayList<MultiChoice> singleChoices = new ArrayList<>();
		singleChoices.add(singleChoice);
		choicePaper.setChoices(singleChoices);
		choicePaper.setSingle(true);
		choicePaperRepository.save(choicePaper);
	}*/

	/*@Test
	public void getPaper(){
		ChoicePaper choicePaper = choicePaperRepository.findAll().get(0);
		log.error(choicePaper.getChoices().get(0).toString());
	}*/

	/*@Test
	public void getSinglePaperByCourse(){
		List<SinglePaper> singlePaperList = singlePaperRepository.findByBelongInfoCourseIdAndBelongInfoChapterId("58c4f9e6582f1708e4f88cbc", "58c4f9e6582f1708e4f88cbd");
		for(SinglePaper singlePaper : singlePaperList){
			log.error(singlePaper.getId()+"==="+singlePaper.getPaperName());
		}

	}*/

	/*@Test
	public void insertAppUser(){
		AppUser appUser = new AppUser();
		appUser.setPhone("18860902711");
		appUser.setUsername("qbank_2711");
		appUser = appUserRepository.save(appUser);
		System.out.println(appUser.getId());
	}*/
}
