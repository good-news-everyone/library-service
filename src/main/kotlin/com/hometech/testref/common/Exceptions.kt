package com.hometech.testref.common

abstract class BaseException(msg: String): RuntimeException(msg)

class NotFoundException(msg: String): BaseException(msg)
class BusinessException(msg: String): BaseException(msg)
