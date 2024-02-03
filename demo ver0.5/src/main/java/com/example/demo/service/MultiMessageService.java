package com.example.demo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bean.MappingObject;
import com.example.demo.bean.MultiMessage;
import com.example.demo.repository.MultiMessageRepository;

@Service
public class MultiMessageService {

	@Autowired
	MultiMessageRepository multiMessageRepo;

	/**
	 * メッセージListを取得し、Mapに変換する（Springキャシュを実装する予定）
	 * @return
	 */
	public Map<String, MultiMessage> getMessages() {
		Map<String, MultiMessage> messages = MappingObject.toMap(multiMessageRepo.select()); // リポジトリから取得したListをMapに変換する
		return messages;
	}

	/**
	 * メッセージを取得する
	 * @param messageID：メッセージID
	 * @param lang：言語
	 * @return　メッセージ
	 */
	public String getMessage(String messageID, String lang) {

		Map<String, MultiMessage> messageMap = getMessages();
		String message = "";

		if (messageID == null) {
			messageID = "";
		}

		if (lang == null) {
			lang = "en";
		}

		MultiMessage messageBean = messageMap.get(messageID + lang);

		if (messageBean != null) {
			message = messageBean.getMessage();
		}

		// メッセージを取得できない場合、英語のメッセージを再度取得する
		if (!"en".equals(lang) && (message == null || "".equals(message))) {
			messageBean = messageMap.get(messageID + "en");
			if (messageBean != null) {
				message = messageBean.getMessage();
			}
		}

		if (message == null) {
			message = "";
		}

		return message;
	}
}